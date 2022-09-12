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
                loadingLiveData.postValue(true)
                ktorVideoRepository.getSearchVideo(keyword)
            }.onSuccess {
                loadingLiveData.postValue(false)
                videoList.value = it
            }.onFailure {
                loadingLiveData.postValue(false)
            }
        }
    }

}