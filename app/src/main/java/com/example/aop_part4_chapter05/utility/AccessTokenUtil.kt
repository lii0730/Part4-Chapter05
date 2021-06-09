package com.example.aop_part4_chapter05.utility

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.map

class AccessTokenUtil(val context: Context) {

    lateinit var dataStore:DataStore<androidx.datastore.preferences.core.Preferences>

    private object PreferencesKeys {
        val KEY_AUTH_TOKEN = stringPreferencesKey("auth_token")
    }

    suspend fun saveAccessToken(token: String) {
        dataStore = context.createDataStore(KEY_AUTH_TOKEN)
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_AUTH_TOKEN] = token
        }
    }

//    suspend fun getAccessToken() : String {
//        var auth_token : String
//        dataStore.data.map { preferences ->
//            auth_token = preferences[PreferencesKeys.KEY_AUTH_TOKEN] ?: ""
//        }
//        return auth_token
//    }

    companion object {
        const val KEY_AUTH_TOKEN: String = "auth_token"
        const val TOKEN: String = "token"
    }
}