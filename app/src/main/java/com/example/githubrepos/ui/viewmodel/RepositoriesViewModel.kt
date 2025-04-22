package com.example.githubrepos.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.data.model.Repository
import com.example.githubrepos.data.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private val _repositories = MutableStateFlow<List<Repository>>(emptyList())
    val repositories: StateFlow<List<Repository>> = _repositories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentPage = 1
    private var username = "google" // Default username

    fun setUsername(newUsername: String) {
        username = newUsername
        currentPage = 1
        _repositories.value = emptyList()
        loadRepositories()
    }

    fun loadRepositories() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val newRepos = repository.getRepositories(username, currentPage)
                _repositories.value = _repositories.value + newRepos
                currentPage++

                if (newRepos.isEmpty()) {
                    _error.value = "No more repositories to load"
                }
            } catch (e: Exception) {
                _error.value = when (e) {
                    is HttpException -> {
                        when (e.code()) {
                            404 -> "User not found"
                            403 -> "API rate limit exceeded"
                            else -> "An error occurred: ${e.message()}"
                        }
                    }
                    is IOException -> "Network error. Please check your connection."
                    else -> "An unexpected error occurred: ${e.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}