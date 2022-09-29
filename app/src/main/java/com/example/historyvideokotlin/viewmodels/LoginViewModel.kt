package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(application: Application) : BaseViewModel(application) {
    val auth = Firebase.auth
    var disposable: Disposable? = null
    var userRepository = UserRepository()
    var userList = MutableLiveData<List<User>>()


    fun loginWithEmail(email: String, password: String): Boolean {

        var isUserLogin = false

        if (email.isEmpty() || password.isEmpty()) {
            isUserLogin = true
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        MyLog.e("uid", task.result.user?.uid ?: "null")
                        getUser(task.result.user?.uid ?: "null")
                        isUserLogin = false
                    }
                }.addOnFailureListener { e ->
                    isUserLogin = true
                }
        }

        return isUserLogin
    }

    fun getUser(userId: String) {
        disposable =
            userRepository.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribe(
                    { data ->
                        data.let {
                            userList.value = it
                        }
                    },
                    { error -> MyLog.e("this", error.message.toString()) }
                )
    }
}