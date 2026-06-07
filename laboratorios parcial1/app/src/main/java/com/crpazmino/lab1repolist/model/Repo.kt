package com.crpazmino.lab1repolist.model

import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Long,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    val description: String?
)