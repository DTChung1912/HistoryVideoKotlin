package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.launch

class MyPageViewModel(application: Application) :
    BaseViewModel(application) {
    val userDetail = MutableLiveData<User>()

    var userId = HistoryUserManager.instance.UserId()

    val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    fun getUserDetail() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getUser(userId)
            }.onSuccess {
                MyLog.e("userDetail", it.toString())
                userDetail.value = it
                hideLoading()
            }.onFailure {
                hideLoading()
                MyLog.e("getUser", it.message)
            }
        }
    }
}
