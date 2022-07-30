package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.Repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MyPageViewModel(application: Application) :
    BaseViewModel(application) {

    private var _textName = MutableLiveData<String>()
    val textName: LiveData<String> get() = _textName
    var disposable2 = CompositeDisposable()
    var user = MutableLiveData<List<User>>()
    var userRepository =  UserRepository()

    fun refreshUser() {
        getUser()
    }

    private fun getUser() {
        disposable2.add(
            userRepository.getUser2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<User>>() {
                    override fun onSuccess(t: List<User>) {
                        user.value = t
                        Log.e("chung" , "Ok")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("chung" , e.message.toString())
                    }

                })
        )

    }

}