package com.crpazmino.lab1repolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.crpazmino.lab1repolist.ui.theme.Lab1RepoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1RepoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RepoList()
                }
            }
        }
    }
}