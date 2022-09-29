package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class UserEditViewModel(application: Application) : BaseViewModel(application) {

    var disposable: Disposable? = null
    var userRepository = UserRepository()
    var userList = MutableLiveData<List<User>>()
    val userInfo = MutableLiveData<User>()
    var userId = HistoryUserManager.instance.UserId()

    val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    fun getUser() {
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

    fun updateUser(user: User) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.updateUser(user)
            }.onSuccess {
                hideLoading()
                userInfo.value = it
            }.onFailure {
                hideLoading()
                MyLog.e("updateUser",it.message)
            }
        }
    }
}