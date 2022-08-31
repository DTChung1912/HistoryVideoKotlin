package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class KtorVideoRepository(val apiService: KtorAPIService) {
    suspend fun getVideo(): List<Video> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getVideo()
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getComment(videoId: Int): List<Comment> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getComment(videoId)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun register(user: User): List<User> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.register(user)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getVideo(userId: String, videoId: Int): List<MyVideo> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getMyVideo(userId, videoId)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }
}