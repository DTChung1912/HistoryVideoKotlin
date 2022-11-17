package com.example.historyvideokotlin.repository

import androidx.lifecycle.LiveData
import com.example.historyvideokotlin.data.PostDao
import com.example.historyvideokotlin.model.DownloadPost

class DownloadPostRepository(val postDao: PostDao) {
    val readAllData: LiveData<List<DownloadPost>> = postDao.readAllData()

    suspend fun insertPost(post: DownloadPost) {
        postDao.insertPost(post)
    }

    suspend fun updatePost(post: DownloadPost) {
        postDao.updatePost(post)
    }

    suspend fun deletePost(post: DownloadPost) {
        postDao.deletePost(post)
    }

    suspend fun deleteAllPosts() {
        postDao.deleteAllPosts()
    }
}