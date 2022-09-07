package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

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
}