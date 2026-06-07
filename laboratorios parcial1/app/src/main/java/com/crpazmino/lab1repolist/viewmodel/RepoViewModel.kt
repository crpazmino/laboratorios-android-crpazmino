package com.crpazmino.lab1repolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crpazmino.lab1repolist.model.CreateRepoRequest
import com.crpazmino.lab1repolist.model.Repo
import com.crpazmino.lab1repolist.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RepoUiState {
    object Loading : RepoUiState()
    data class Success(val repos: List<Repo>) : RepoUiState()
    data class Error(val message: String) : RepoUiState()
}

sealed class CreateRepoState {
    object Idle : CreateRepoState()
    object Loading : CreateRepoState()
    object Success : CreateRepoState()
    data class Error(val message: String) : CreateRepoState()
}

class RepoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<RepoUiState>(RepoUiState.Loading)
    val uiState: StateFlow<RepoUiState> = _uiState

    private val _createState = MutableStateFlow<CreateRepoState>(CreateRepoState.Idle)
    val createState: StateFlow<CreateRepoState> = _createState

    init {
        fetchRepos("crpazmino")
    }

    fun fetchRepos(username: String) {
        viewModelScope.launch {
            _uiState.value = RepoUiState.Loading
            try {
                val repos = RetrofitClient.apiService.getRepos(username)
                _uiState.value = RepoUiState.Success(repos)
            } catch (e: Exception) {
                _uiState.value = RepoUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun createRepo(token: String, name: String, description: String) {
        viewModelScope.launch {
            _createState.value = CreateRepoState.Loading
            try {
                val request = CreateRepoRequest(name = name, description = description)
                RetrofitClient.apiService.createRepo("Bearer $token", request)
                _createState.value = CreateRepoState.Success
                fetchRepos("crpazmino")
            } catch (e: Exception) {
                _createState.value = CreateRepoState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetCreateState() {
        _createState.value = CreateRepoState.Idle
    }
}