package com.example.aop_part4_chapter05.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aop_part4_chapter05.Entity.GitHubRepos


@Database(entities = [GitHubRepos::class], version = 1, exportSchema = false)
abstract class FavoriteRepoDatabase : RoomDatabase() {

    abstract fun favoriteDao() : FavoriteRepoDao

}