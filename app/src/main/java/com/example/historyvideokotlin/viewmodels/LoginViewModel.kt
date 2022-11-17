package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : BaseViewModel(application) {
    private val auth = Firebase.auth
    private val user = MutableLiveData<User>()
    private val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    fun loginWithEmail(email: String, password: String): Boolean {
        var isUserLogin = false
        if (email.isEmpty() || password.isEmpty()) {
            isUserLogin = false
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        MyLog.e("UserId", task.result.user?.uid ?: "null")
                        getUser(task.result.user?.uid ?: "null")
                        isUserLogin = true
                    }
                }.addOnFailureListener { e ->
                    isUserLogin = false
                }
        }
        return isUserLogin
    }

    fun getUser(userId: String) {
        viewModelScope.launch {
            runCatching {
                ktorUserRepository.getUser(userId)
            }.onSuccess {
                user.value = it
            }.onFailure {
                MyLog.e("getUser", it.message)
            }
        }
    }
}
