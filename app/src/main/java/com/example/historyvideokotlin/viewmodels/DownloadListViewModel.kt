package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.data.VideoDatabase
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.repository.DownloadVideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DownloadListViewModel(application: Application) : BaseViewModel(application) {

    val readAllData: LiveData<List<DownloadVideo>>
    private val repository: DownloadVideoRepository

    init {
        val videoDao = VideoDatabase.getDatabase(application).videoDao()
        repository = DownloadVideoRepository(videoDao)
        readAllData = repository.readAllData
    }

    fun insertVideo(video: DownloadVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertVideo(video)
        }
    }

    fun updateVideo(video: DownloadVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateVideo(video)
        }
    }

    fun deleteVideo(video: DownloadVideo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteVideo(video)
        }
    }

    fun deleteAllVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllVideos()
        }
    }
}