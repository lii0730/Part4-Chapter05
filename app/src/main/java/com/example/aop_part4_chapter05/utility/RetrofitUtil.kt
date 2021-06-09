package com.example.aop_part4_chapter05.utility

import com.example.aop_part4_chapter05.Entity.GitHubRepos
import com.example.aop_part4_chapter05.Services.AuthApiService
import com.example.aop_part4_chapter05.Services.GitHubApiService
import com.example.aop_part4_chapter05.URL.URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//TODO: GET Service 필요 -> AccessToken 을 이용하여 사용자 정보 요청 , AccessToken은 Header에 추가    [AccessToken 필요]
//TODO: Post Service 필요 -> CallBack 시 얻어온 code 파라미터를 이용해서 Access Token 요청           [code, client_id, client_secret 필요]

object RetrofitUtil {

    val authApiService : AuthApiService by lazy {
        GithubAuthRetrofit().create(AuthApiService::class.java)
    }

    private fun GithubAuthRetrofit() : Retrofit {
        val gson : Gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(URL.GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val GitHubService : GitHubApiService by lazy {
        GitHubReposRetrofit().create(GitHubApiService::class.java)
    }

    private fun GitHubReposRetrofit() : Retrofit {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(URL.GITHUB_API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}