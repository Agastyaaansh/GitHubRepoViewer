// app/src/main/java/com/example/githubrepos/di/AppModule.kt
package com.example.githubrepos.di

import com.example.githubrepos.data.api.GithubApiService
import com.example.githubrepos.data.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApiService(): GithubApiService {
        return GithubApiService.create()
    }

    @Provides
    @Singleton
    fun provideGithubRepository(apiService: GithubApiService): GithubRepository {
        return GithubRepository(apiService)
    }
}