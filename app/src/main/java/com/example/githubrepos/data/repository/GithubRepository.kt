package com.example.githubrepos.data.repository

import com.example.githubrepos.data.api.GithubApiService
import com.example.githubrepos.data.model.Repository
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val apiService: GithubApiService
) {
    suspend fun getRepositories(username: String, page: Int, perPage: Int = 30): List<Repository> {
        return apiService.getRepositories(username, page, perPage)
    }
}