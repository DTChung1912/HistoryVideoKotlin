package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserEditViewModel(application: Application) : BaseViewModel(application) {

    var disposable: Disposable? = null
    var userRepository = UserRepository()
    var userList = MutableLiveData<List<User>>()
    var userId = HistoryUserManager.FUid()

    fun getUser() {
        disposable =
            userRepository.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
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