package com.example.aop_part4_chapter05.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aop_part4_chapter05.Entity.GitHubRepos

@Dao
interface FavoriteRepoDao {

    @Query("SELECT * FROM GitHubRepos")
    fun getAllFavoriteRepo() : List<GitHubRepos>

    @Query("SELECT * FROM GitHubRepos WHERE full_name =:fullName")
    fun getFavoriteRepo(fullName: String) : GitHubRepos?

    @Insert
    fun insertRepo(repo : GitHubRepos)

    @Query("DELETE FROM GitHubRepos WHERE full_name =:fullName")
    fun deleteRepo(fullName : String)
}