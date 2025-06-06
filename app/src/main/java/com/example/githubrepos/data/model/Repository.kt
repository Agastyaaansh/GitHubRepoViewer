package com.example.githubrepos.data.model

data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    val stargazers_count: Int,
    val forks_count: Int,
    val html_url: String
)