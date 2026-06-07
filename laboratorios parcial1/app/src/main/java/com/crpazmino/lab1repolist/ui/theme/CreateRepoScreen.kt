package com.crpazmino.lab1repolist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.crpazmino.lab1repolist.viewmodel.CreateRepoState
import com.crpazmino.lab1repolist.viewmodel.RepoViewModel

@Composable
fun CreateRepoScreen(
    viewModel: RepoViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val createState by viewModel.createState.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Crear",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre del proyecto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción del proyecto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.createRepo("", name, description)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank()
        ) {
            Text("Crear")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (createState) {
            is CreateRepoState.Loading -> CircularProgressIndicator()
            is CreateRepoState.Success -> {
                Text("¡Repositorio creado exitosamente!", color = Color.Green)
                viewModel.resetCreateState()
            }
            is CreateRepoState.Error -> {
                val msg = (createState as CreateRepoState.Error).message
                Text("Error: $msg", color = Color.Red)
            }
            else -> {}
        }
    }
}