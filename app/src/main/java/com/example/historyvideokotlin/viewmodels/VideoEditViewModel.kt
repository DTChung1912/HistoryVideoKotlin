package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class VideoEditViewModel(application: Application) : BaseViewModel(application) {
    private val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository
    private val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    private val _isUpdate = MutableLiveData(false)
    val isUpdate: LiveData<Boolean> get() = _isUpdate

    val isUpdateComplete = MutableLiveData<Boolean>()
    val isAddComplete = MutableLiveData<Boolean>()

    private val _video = MutableLiveData<Video>()
    val video: LiveData<Video> get() = _video

    private val themes = mutableListOf<Theme>()
    private val themArray = mutableListOf<String>()
    val themeList = MutableLiveData<List<String>>()

    fun setIsUpdate(video: Video) {
        if (video.video_id == 0) {
            _isUpdate.value = false
        } else {
            _isUpdate.value = true
            _video.value = video
        }
    }

    fun getThemeList() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorQuizRepository.getTheme()
            }.onSuccess {
                themes.addAll(it)
                getThemeString()
                themeList.value = themArray
                hideLoading()
            }.onFailure {
                MyLog.e("getTheme", it.message)
                hideLoading()
            }
        }
    }

    private fun getThemeString() {
        for (i in themes) {
            themArray.add(i.theme_name)
        }
    }

    fun updateVideo(video: Video) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateVideo(video))
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

    fun createVideo(video: Video) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.createVideo(video))
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
