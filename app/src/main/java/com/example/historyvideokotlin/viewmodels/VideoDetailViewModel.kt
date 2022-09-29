package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.MyVideoStatus
import com.example.historyvideokotlin.repository.HistoryUserManager
import kotlinx.coroutines.launch

class VideoDetailViewModel(application: Application) : BaseViewModel(application) {

    val ktorUserRepository = application.repositoryProvider.ktorUserRepository
    val userId = HistoryUserManager.instance.UserId()
    val myVideoStatus = MediatorLiveData<MyVideoStatus>()

    fun getMyVideo(videoId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                ktorUserRepository.getMyVideo(userId,videoId)
            }.onSuccess {
                myVideoStatus.value = it[0]
            }.onFailure {

            }
        }
    }

    fun updateView(videoId: Int, duration: Int) {
        if (userId.isNotEmpty()) {
            val myVideo = MyVideoStatus(0, userId, videoId, 0, 0, 0, 1, 0, 0, 0, duration)
            viewModelScope.launch {
                kotlin.runCatching {
                    ktorUserRepository.postMyVideo(myVideo)
                }.onSuccess {

                }.onFailure {

                }
            }
        }

    }

    fun getTabTitleIds(): List<Int> {
        val titleIds = mutableListOf<Int>()
        titleIds.run {
            add(R.string.title_video_tab_next_video)
            add(R.string.title_video_tab_commet)
        }
        return titleIds
    }
}
