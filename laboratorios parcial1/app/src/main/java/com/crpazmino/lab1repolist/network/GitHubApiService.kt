package com.crpazmino.lab1repolist.network

import com.crpazmino.lab1repolist.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username") username: String): List<Repo>
}