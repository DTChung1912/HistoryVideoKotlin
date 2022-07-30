package com.example.historyvideokotlin.viewmodels

import android.app.Application
import com.example.historyvideokotlin.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel(application: Application) : BaseViewModel(application) {
    val firebaseAuth = FirebaseAuth.getInstance()

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