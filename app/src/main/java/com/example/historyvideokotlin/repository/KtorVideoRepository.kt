package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.*
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

    suspend fun getMyVideo(userId: String, videoId: Int): List<MyVideo> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getMyVideo(userId, videoId)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun updateVideoLike(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoLike(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess,body.data)
            } else {
                UpdateResponse(false,"")
            }
        }
    }

    suspend fun updateVideoLikeCancel(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoLikeCancel(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess,body.data)
            } else {
                UpdateResponse(false,"")
            }
        }
    }

    suspend fun updateVideoDislike(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoDislike(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess,body.data)
            } else {
                UpdateResponse(false,"")
            }
        }
    }

    suspend fun updateVideoDislikeCancel(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoDislikeCancel(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess,body.data)
            } else {
                UpdateResponse(false,"")
            }
        }
    }

    suspend fun updateVideoDownload(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoDownload(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess,body.data)
            } else {
                UpdateResponse(false,"")
            }
        }
    }

    suspend fun updateVideoView(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoView(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess,body.data)
            } else {
                UpdateResponse(false,"")
            }
        }
    }

    suspend fun updateVideoComment(videoId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.updateVideoComment(videoId)
            val body = reponse.body()!!
            if (reponse.isSuccessful) {
                UpdateResponse(body.isSuccess,body.data)
            } else {
                UpdateResponse(false,"")
            }
        }
    }
}