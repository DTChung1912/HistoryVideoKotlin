package com.example.historyvideokotlin.viewmodels

import android.app.Application
import com.example.historyvideokotlin.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(application: Application) : BaseViewModel(application) {
    val auth = FirebaseAuth.getInstance()

    fun forgotPassword() {

    }

    fun loginWithEmail(email: String, password: String) : Boolean {

        var isUserLogin = false

        if (email.isEmpty() || password.isEmpty()) {
            isUserLogin = true
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        isUserLogin = false
                    }
                }.addOnFailureListener { e ->
                   isUserLogin = true
                }
        }

        return isUserLogin
    }

    fun loginWithFacebook() {

    }
}