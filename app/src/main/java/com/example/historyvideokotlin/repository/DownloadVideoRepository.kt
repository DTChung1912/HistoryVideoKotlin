package com.example.historyvideokotlin.repository

import androidx.lifecycle.LiveData
import com.example.historyvideokotlin.data.VideoDao
import com.example.historyvideokotlin.model.DownloadVideo

class DownloadVideoRepository(private val videoDao: VideoDao) {
    val readAllData: LiveData<List<DownloadVideo>> = videoDao.readAllData()

    suspend fun insertVideo(video: DownloadVideo) {
        videoDao.insertVideo(video)
    }

    suspend fun updateVideo(video: DownloadVideo) {
        videoDao.updateVideo(video)
    }

    suspend fun deleteVideo(video: DownloadVideo) {
        videoDao.deleteVideo(video)
    }

    suspend fun deleteAllVideos() {
        videoDao.deleteAllVideos()
    }
}