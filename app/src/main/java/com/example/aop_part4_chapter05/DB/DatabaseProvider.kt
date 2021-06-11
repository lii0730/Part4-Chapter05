package com.example.aop_part4_chapter05.DB

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun getFavoriteDB(context: Context) = Room.databaseBuilder(
        context,
        FavoriteRepoDatabase::class.java,
        "FavoriteRepo_DB"
    ).build()
}