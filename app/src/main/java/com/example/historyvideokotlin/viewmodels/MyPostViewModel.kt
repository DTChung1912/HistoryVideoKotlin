package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.MyPostResponse
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.launch

class MyPostViewModel(application: Application) : BaseViewModel(application) {
    var myPostResponse = MutableLiveData<MyPostResponse>()

    val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    val userId = HistoryUserManager.instance.UserId()

    fun getData() {
        getMyPostList()
    }

    private fun getMyPostList() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getMyPostList(userId)
            }.onSuccess {
                myPostResponse.value = it
            }.onFailure {
                MyLog.e("getMyPostList", it.message)
            }
        }
    }
}
