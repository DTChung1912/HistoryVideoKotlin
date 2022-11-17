package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class VideoManagementViewModel(application: Application) : BaseViewModel(application) {
    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    val videoList = MutableLiveData<List<Video>>()

    fun getVideoList() {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.getVideoList()
            }.onSuccess {
                videoList.value = it
            }.onFailure {
                MyLog.e("getVideoList", it.message)
            }
        }
    }

    fun deleteVideo(videoId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.deleteVideo(videoId))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("deleteVideo", it.data.toString())
                }
            }
        }
    }
}
