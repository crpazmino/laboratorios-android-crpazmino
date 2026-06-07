package com.crpazmino.lab1repolist.network

import com.crpazmino.lab1repolist.model.CreateRepoRequest
import com.crpazmino.lab1repolist.model.Repo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): List<Repo>

    @POST("user/repos")
    suspend fun createRepo(
        @Header("Authorization") token: String,
        @Body request: CreateRepoRequest
    ): Repo
}