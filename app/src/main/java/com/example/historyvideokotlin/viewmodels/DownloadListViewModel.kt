package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.data.HistoryDatabase
import com.example.historyvideokotlin.model.DownloadPost
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.repository.DownloadPostRepository
import com.example.historyvideokotlin.repository.DownloadVideoRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DownloadListViewModel(application: Application) : BaseViewModel(application) {

    val videoList: LiveData<List<DownloadVideo>>
    val postList: LiveData<List<DownloadPost>>
    private val repository: DownloadVideoRepository
    private val repository2: DownloadPostRepository

    private val _isEmpty = MutableLiveData(true)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    init {
        val database = HistoryDatabase.getDatabase(application)
        val videoDao = database.videoDao()
        val postDao = database.postDao()
        repository = DownloadVideoRepository(videoDao)
        repository2 = DownloadPostRepository(postDao)
        videoList = repository.readAllData
        postList = repository2.readAllData
        _isEmpty.value = postList.value.isNullOrEmpty()
    }

    fun insertVideo(video: DownloadVideo) {
        viewModelScope.launch(IO) {
            repository.insertVideo(video)
        }
    }

    fun updateVideo(video: DownloadVideo) {
        viewModelScope.launch(IO) {
            repository.updateVideo(video)
        }
    }

    fun deleteVideo(video: DownloadVideo) {
        viewModelScope.launch(IO) {
            repository.deleteVideo(video)
        }
    }

    fun deleteAllVideos() {
        viewModelScope.launch(IO) {
            repository.deleteAllVideos()
        }
    }

    fun insertPost(post: DownloadPost) {
        viewModelScope.launch(IO) {
            repository2.insertPost(post)
        }
    }

    fun updatePost(post: DownloadPost) {
        viewModelScope.launch(IO) {
            repository2.updatePost(post)
        }
    }

    fun deletePost(post: DownloadPost) {
        viewModelScope.launch(IO) {
            repository2.deletePost(post)
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch(IO) {
            repository2.deleteAllPosts()
        }
    }
}
