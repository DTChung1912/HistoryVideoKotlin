package com.example.historyvideokotlin.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object HistoryUserManager {

    fun checkUserLogined() : Boolean{
        val user = Firebase.auth.currentUser
        if (user != null) {
            return true
        }else {
            return false
        }
    }

    fun FUid() : String {
        var id = Firebase.auth.currentUser?.uid
        if (id == null) {
            id = "null"
        }
        return id
    }

}