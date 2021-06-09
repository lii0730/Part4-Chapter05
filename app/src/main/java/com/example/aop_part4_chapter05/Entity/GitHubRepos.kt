package com.example.aop_part4_chapter05.Entity

import com.google.gson.annotations.SerializedName

data class GitHubRepos(
    @SerializedName("full_name") val full_name : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("owner") val owner: GitHubOwner,
    @SerializedName("stargazers_count") val stargazers_count : Int,
    @SerializedName("language") val language : String,
    @SerializedName("description") val description : String
)
