package com.example.aop_part4_chapter05.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteRepo(
    @PrimaryKey val full_name: String?,
    @ColumnInfo val updated_at: String?,
    val owner: GitHubOwner?,
    val stargazers_count: Int?,
    val language: String?,
    val description: String?
)
