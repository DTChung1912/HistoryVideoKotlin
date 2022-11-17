package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UserManagementViewModel(application: Application) : BaseViewModel(application) {
    private val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    val userList = MutableLiveData<List<User>>()

    fun getUserList() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getUserList()
            }.onSuccess {
                hideLoading()
                userList.value = it
            }.onFailure {
                MyLog.e("getUserList", it.message)
                hideLoading()
            }
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorUserRepository.deleteUser(userId))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("deleteUser", it.data.toString())
                }
            }
        }
    }
}
