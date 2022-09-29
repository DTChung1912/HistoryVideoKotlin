package com.example.historyvideokotlin.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.historyvideokotlin.HistoryApplication
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class HistoryUserManager {

    private val preferences: SharedPreferences
    private val preferencesApp: SharedPreferences
    private val gson: Gson

    fun checkUserLogined(): Boolean {
        val user = Firebase.auth.currentUser
        if (user != null) {
            return true
        } else {
            return false
        }
    }

    fun UserId(): String {
        var id = Firebase.auth.currentUser?.uid
        if (id == null) {
            return ""
        }

        return id
    }

    init {
        preferences =
            HistoryApplication.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        preferencesApp =
            HistoryApplication.instance.getSharedPreferences(PREF_APP_NAME, Context.MODE_PRIVATE)
        gson = Gson()
    }

    companion object {
        const val PREF_NAME = "HistoryPref"
        const val PREF_APP_NAME = "HistoryAppPref"

        var instance = HistoryUserManager()
    }
}