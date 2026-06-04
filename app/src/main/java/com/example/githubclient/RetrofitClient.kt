package com.example.githubclient

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://api.github.com/"
    private const val TOKEN = "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"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "token $TOKEN")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()
            chain.proceed(request)
        }
        .build()

    val api: GitHubApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }
}