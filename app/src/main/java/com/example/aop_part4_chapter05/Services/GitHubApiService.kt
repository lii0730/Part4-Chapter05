package com.example.aop_part4_chapter05.Services

import com.example.aop_part4_chapter05.Entity.GitHubRepos
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {

    @GET("users/{owner}/repos")
    suspend fun getRepos(@Path("owner") owner : String) : List<GitHubRepos>

}