package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PostManagementViewModel(application: Application) : BaseViewModel(application) {
    val ktorPostRepository = application.repositoryProvider.ktorPostRepository

    val postList = MutableLiveData<List<Post>>()

    fun getPostList() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorPostRepository.getPostList()
            }.onSuccess {
                hideLoading()
                postList.value = it
            }.onFailure {
                hideLoading()
                MyLog.e("postList", it.message)
            }
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorPostRepository.deletePost(postId))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("deletePost", it.data.toString())
                }
            }
        }
    }
}
