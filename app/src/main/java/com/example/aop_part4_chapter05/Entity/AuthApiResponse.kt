package com.example.aop_part4_chapter05.Entity

data class AuthApiResponse(
    val access_token: String,
    val scope: String,
    val token_type: String
)
