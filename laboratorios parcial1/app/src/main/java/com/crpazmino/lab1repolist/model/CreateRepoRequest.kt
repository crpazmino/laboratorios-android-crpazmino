package com.crpazmino.lab1repolist.model

import com.google.gson.annotations.SerializedName

data class CreateRepoRequest(
    val name: String,
    val description: String,
    @SerializedName("private")
    val isPrivate: Boolean = false
)