package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.MyPost
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.network.KtorAPIService
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorUserRepository(val apiService: KtorAPIService) {

    suspend fun getUser(userId: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse: Response<List<User>> = apiService.getUser(userId)
            if (reponse.isSuccessful) {
                val body = reponse.body()
                body?.let {
                    MyLog.e("chungnew", it[0].user_id)
                }
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

    suspend fun getMyVideoList(userId: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<List<MyVideo>> = apiService.getMyVideoList(userId)
        }
    }

    suspend fun getMyPostList(userId: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val response: Response<List<MyPost>> = apiService.getMyPostList(userId)
        }
    }
}