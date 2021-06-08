package com.example.aop_part4_chapter05.Services

import com.example.aop_part4_chapter05.DTO.ReposDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {

    @GET("users/{owner}/repos")
    fun getRepos(@Path("owner") owner : String) : Call<ReposDTO>

}