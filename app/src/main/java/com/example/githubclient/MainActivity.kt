package com.example.githubclient

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var repositoryFragment: RepositoryFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repositoryFragment = RepositoryFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, repositoryFragment)
            .commit()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RepositoryFormFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }

        loadRepositories()
    }

    fun loadRepositories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.getRepositories()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val repos = response.body() ?: emptyList()
                        repositoryFragment.updateList(repos)
                    } else {
                        Toast.makeText(this@MainActivity, "Error ${response.code()}: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Excepción: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onEditRepository(repo: Repository) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, RepositoryFormFragment.newInstance(repo))
            .addToBackStack(null)
            .commit()
    }

    fun onDeleteRepository(repo: Repository) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar repositorio")
            .setMessage("¿Estás seguro de eliminar ${repo.name}?")
            .setPositiveButton("Eliminar") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    val username = "crpazmino"
                    val response = RetrofitClient.api.deleteRepository(username, repo.name)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity, "Repositorio eliminado", Toast.LENGTH_SHORT).show()
                            loadRepositories()
                        } else {
                            Toast.makeText(this@MainActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}