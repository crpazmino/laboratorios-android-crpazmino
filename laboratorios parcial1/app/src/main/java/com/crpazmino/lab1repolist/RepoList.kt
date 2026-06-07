package com.crpazmino.lab1repolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RepoList() {
    val repositories = listOf(
        "android/architecture-samples",
        "google/accompanist",
        "square/retrofit",
        "bumptech/glide",
        "ReactiveX/RxJava",
        "JakeWharton/timber",
        "airbnb/lottie-android",
        "coil-kt/coil"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Repositorios",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        repositories.forEach { repo ->
            RepoItem(repoName = repo)
        }
    }
}