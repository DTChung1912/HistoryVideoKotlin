package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.UpdateResponse
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorPostRepository(val apiService: KtorAPIService) {

    suspend fun getPost(): List<Post> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getPost()
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getSearchPost(keyword: String): List<Post> = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse = apiService.getSearchPost(keyword)
            if (reponse.isSuccessful) {
                reponse.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun updatePostRead(postId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse: Response<UpdateResponse> = apiService.updatePostRead(postId)
            if (reponse.isSuccessful) {
                val body = reponse.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }

    suspend fun updatePostDownload(postId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse: Response<UpdateResponse> = apiService.updatePostDownload(postId)
            if (reponse.isSuccessful) {
                val body = reponse.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }

    suspend fun updatePostRate(postId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse: Response<UpdateResponse> = apiService.updatePostRate(postId)
            if (reponse.isSuccessful) {
                val body = reponse.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }
}