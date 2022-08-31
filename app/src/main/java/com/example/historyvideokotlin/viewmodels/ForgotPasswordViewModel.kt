package com.example.historyvideokotlin.viewmodels

import android.app.Application
import com.example.historyvideokotlin.base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordViewModel(application: Application) : BaseViewModel(application) {
    val firebaseAuth = Firebase.auth

    fun resetPassword(email : String) : Boolean {
        var isReset = false
        if (email.isEmpty()) {
            return false
        }
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isReset = true
                }
            }.addOnFailureListener { e ->
                isReset = false
            }
        return isReset
    }

}