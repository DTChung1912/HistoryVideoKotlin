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

class UserEditViewModel(application: Application) : BaseViewModel(application) {
    val userInfo = MutableLiveData<User>()
    var userId = HistoryUserManager.instance.UserId()

    val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    val isUpdateComplete = MutableLiveData<Boolean>()
    val isAddComplete = MutableLiveData<Boolean>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _isUpdate = MutableLiveData(false)
    val isUpdate: LiveData<Boolean> get() = _isUpdate

    fun setUser(user: User) {
        if (user.user_id == "") {
            _isUpdate.value = false
        } else {
            _isUpdate.value = true
            _user.value = user
        }
    }

    fun register(user: User) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.register(user)
            }.onSuccess {
                hideLoading()
                userInfo.value = it[0]
                isAddComplete.value = true
            }.onFailure {
                hideLoading()
                isAddComplete.value = false
                MyLog.e("register", it.message)
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.updateUser(user)
            }.onSuccess {
                hideLoading()
                userInfo.value = it
                isUpdateComplete.value = true
            }.onFailure {
                hideLoading()
                isUpdateComplete.value = false
                MyLog.e("updateUser", it.message)
            }
        }
    }
}
