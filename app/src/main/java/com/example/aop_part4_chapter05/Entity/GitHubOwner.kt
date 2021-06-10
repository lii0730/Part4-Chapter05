package com.example.aop_part4_chapter05.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubOwner(
    val login : String,
    val avatar_url : String
) : Parcelable
