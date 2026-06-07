package com.crpazmino.lab1repolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.crpazmino.lab1repolist.model.Repo
import com.crpazmino.lab1repolist.ui.CreateRepoScreen
import com.crpazmino.lab1repolist.ui.EditRepoScreen
import com.crpazmino.lab1repolist.ui.theme.Lab1RepoListTheme
import com.crpazmino.lab1repolist.viewmodel.RepoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1RepoListTheme {
                val viewModel: RepoViewModel = viewModel()
                var showCreate by remember { mutableStateOf(false) }
                var selectedRepo by remember { mutableStateOf<Repo?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (!showCreate && selectedRepo == null) {
                            FloatingActionButton(
                                onClick = { showCreate = true }
                            ) {
                                Text("➕")
                            }
                        }
                    }
                ) { innerPadding ->
                    when {
                        selectedRepo != null -> {
                            val repo = selectedRepo!!
                            val parts = repo.fullName.split("/")
                            EditRepoScreen(
                                owner = parts[0],
                                repoName = parts[1],
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding),
                                onBack = {
                                    selectedRepo = null
                                    viewModel.resetActionState()
                                }
                            )
                        }
                        showCreate -> {
                            CreateRepoScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding),
                                onCreated = {
                                    showCreate = false
                                    viewModel.resetCreateState()
                                }
                            )
                        }
                        else -> {
                            RepoList(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding),
                                onEditRepo = { repo -> selectedRepo = repo }
                            )
                        }
                    }
                }
            }
        }
    }
}