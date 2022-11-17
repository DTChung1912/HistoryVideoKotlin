package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.launch

class UserInfoViewModel(application: Application) : BaseViewModel(application) {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val userId = HistoryUserManager.instance.UserId()
    private val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    fun setUser(user: User) {
        _user.value = user
    }

    fun getUserInfo() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getUser(userId)
            }.onSuccess {
                _user.value = it
                hideLoading()
            }.onFailure {
                MyLog.e("postList", it.message)
                hideLoading()
            }
        }
    }
}
