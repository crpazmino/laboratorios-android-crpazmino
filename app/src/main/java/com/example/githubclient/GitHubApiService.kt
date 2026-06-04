package com.example.githubclient

import retrofit2.Response
import retrofit2.http.*

interface GitHubApiService {

    @GET("user/repos")
    suspend fun getRepositories(): Response<List<Repository>>

    @POST("user/repos")
    suspend fun createRepository(@Body repo: Map<String, String>): Response<Repository>

    @PATCH("repos/{owner}/{repo}")
    suspend fun updateRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body body: Map<String, String>
    ): Response<Repository>

    @DELETE("repos/{owner}/{repo}")
    suspend fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>
}