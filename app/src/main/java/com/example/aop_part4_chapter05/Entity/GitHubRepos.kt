package com.example.aop_part4_chapter05.Entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class GitHubRepos(
    @PrimaryKey val full_name: String,
    @ColumnInfo val updated_at: String?,
    @Embedded val owner: GitHubOwner?,
    @ColumnInfo val stargazers_count: Int?,
    @ColumnInfo val language: String?,
    @ColumnInfo val description: String?
) : Parcelable
