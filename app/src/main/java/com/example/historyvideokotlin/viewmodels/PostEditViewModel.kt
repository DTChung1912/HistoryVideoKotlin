package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.model.PostRequest
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PostEditViewModel(application: Application) : BaseViewModel(application) {
    private val ktorPostRepository = application.repositoryProvider.ktorPostRepository

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    private val _isUpdate = MutableLiveData(false)
    val isUpdate: LiveData<Boolean> get() = _isUpdate

    val isUpdateComplete = MutableLiveData<Boolean>()
    val isAddComplete = MutableLiveData<Boolean>()

    private val postTypes =
        listOf("Nhân vật lịch sử", "Sự kiện lịch sử", "Địa danh lịch sử", "Mốc thời gian")

    val postTypeList = MutableLiveData<List<String>>(postTypes)

    fun setPost(post: Post) {
        _post.value = post
        _isUpdate.value = if (post.post_id == 0) false else true
    }

    fun updatePost(post: PostRequest) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorPostRepository.updatePost(post))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    isUpdateComplete.value = it.isSuccess
                    MyLog.e("updateVideo", it.data.toString())
                }
            }
        }
    }

    fun createPost(post: PostRequest) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorPostRepository.createPost(post))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    isAddComplete.value = it.isSuccess
                    MyLog.e("postVideo", it.data.toString())
                }
            }
        }
    }
}
