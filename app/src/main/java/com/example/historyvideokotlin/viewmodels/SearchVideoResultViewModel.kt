package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Video
import kotlinx.coroutines.launch

class SearchVideoResultViewModel(application: Application) : BaseViewModel(application) {

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    var videoList = MutableLiveData<List<Video>>()

    fun getSeacrhVideo(keyword: String) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorVideoRepository.getSearchVideo(keyword)
            }.onSuccess {
                hideLoading()
                videoList.value = it
            }.onFailure {
                hideLoading()
            }
        }
    }

}