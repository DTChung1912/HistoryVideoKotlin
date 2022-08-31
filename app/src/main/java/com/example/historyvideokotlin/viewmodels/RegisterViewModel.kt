package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterViewModel(application: Application) : BaseViewModel(application) {

    val userRepository = UserRepository()
    val disposable = CompositeDisposable()
    var disposable2: Disposable? = null
    var user = MutableLiveData<List<User>>()


    fun postUser(userId: String, name: String, email: String) {
        disposable2 = userRepository.postUser2(userId,name, email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingLiveData.postValue(true) }
            .doAfterTerminate { loadingLiveData.postValue(false) }
            .subscribe(
                { result -> MyLog.e("chung", result.toString()) },
                { error -> MyLog.e("this", error.message.toString()) },
                { Log.i("TAG", "Login Completed") }
            )
    }
}