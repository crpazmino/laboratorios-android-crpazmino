package com.example.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryFormFragment : Fragment() {

    private var repoToEdit: Repository? = null

    companion object {
        fun newInstance(repo: Repository? = null): RepositoryFormFragment {
            val fragment = RepositoryFormFragment()
            repo?.let {
                val args = Bundle()
                args.putLong("id", it.id)
                args.putString("name", it.name)
                args.putString("description", it.description ?: "")
                fragment.arguments = args
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repoToEdit = Repository(
                id = it.getLong("id"),
                name = it.getString("name") ?: "",
                description = it.getString("description")
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName: EditText = view.findViewById(R.id.etRepoName)
        val etDescription: EditText = view.findViewById(R.id.etRepoDescription)
        val btnSave: Button = view.findViewById(R.id.btnSave)

        repoToEdit?.let {
            etName.setText(it.name)
            etName.isEnabled = false
            etDescription.setText(it.description)
        }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val description = etDescription.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (repoToEdit == null) {
                createRepository(name, description)
            } else {
                updateRepository(repoToEdit!!.name, description)
            }
        }
    }

    private fun createRepository(name: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val body = mapOf("name" to name, "description" to description)
            val response = RetrofitClient.api.createRepository(body)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Repositorio creado", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Error al crear", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateRepository(repoName: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val username = "crpazmino"
            val body = mapOf("description" to description)
            val response = RetrofitClient.api.updateRepository(username, repoName, body)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Repositorio actualizado", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}