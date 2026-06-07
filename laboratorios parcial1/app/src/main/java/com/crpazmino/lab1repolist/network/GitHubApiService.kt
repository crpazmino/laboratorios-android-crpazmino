package com.crpazmino.lab1repolist.network

import com.crpazmino.lab1repolist.model.CreateRepoRequest
import com.crpazmino.lab1repolist.model.Repo
import com.crpazmino.lab1repolist.model.UpdateRepoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): List<Repo>

    @Headers("Accept: application/vnd.github+json")
    @POST("user/repos")
    suspend fun createRepo(
        @Header("Authorization") token: String,
        @Body request: CreateRepoRequest
    ): Repo

    @Headers("Accept: application/vnd.github+json")
    @PATCH("repos/{owner}/{repo}")
    suspend fun updateRepo(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body request: UpdateRepoRequest
    ): Repo

    @Headers("Accept: application/vnd.github+json")
    @DELETE("repos/{owner}/{repo}")
    suspend fun deleteRepo(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>
}