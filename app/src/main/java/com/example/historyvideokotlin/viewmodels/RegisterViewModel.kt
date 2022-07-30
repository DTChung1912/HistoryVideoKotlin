package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.Repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(application: Application) : BaseViewModel(application) {

    val userRepository = UserRepository()
    val disposable = CompositeDisposable()
    var disposable2 : Disposable? = null
    var user = MutableLiveData<List<User>>()


    fun postUser(name: String, email: String) {
//        disposable.add(
//            userRepository.postUser(name, email)
//                .andThen(userRepository.getUser2())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    data -> data.let {
//                    user.value = it
//                }
//                },
//                    {   throwable ->
//                        Log.e("HomeActivity", throwable.message ?: "onError")
//                    })
//        )
        disposable2 = userRepository.postUser2(name, email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {result -> Log.e("chung", result.toString())},
                {error -> Log.e("this", error.message.toString())},
                {Log.i("TAG", "Login Completed")}
            )
    }
}