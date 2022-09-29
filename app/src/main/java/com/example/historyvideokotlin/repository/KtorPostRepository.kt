package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.UpdateResponse
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorPostRepository(val apiService: KtorAPIService) {

    suspend fun getPostList(): List<Post> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getPostList()
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getPostDetail(postId: Int): List<Post> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getPostDetail(postId)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getSearchPost(keyword: String): List<Post> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getSearchPost(keyword)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun updatePostRead(postId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<UpdateResponse> = apiService.updatePostRead(postId)
            if (response.isSuccessful) {
                val body = response.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }

    suspend fun updatePostDownload(postId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<UpdateResponse> = apiService.updatePostDownload(postId)
            if (response.isSuccessful) {
                val body = response.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }

    suspend fun updatePostRate(postId: Int): UpdateResponse = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<UpdateResponse> = apiService.updatePostRate(postId)
            if (response.isSuccessful) {
                val body = response.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }
}