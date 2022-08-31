package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.network.KtorAPIService
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorUserRepository(val apiService: KtorAPIService)  {
    suspend fun getUser(userId: String) = coroutineScope {
        withContext(Dispatchers.IO) {
            val reponse : Response<List<User>> = apiService.getUser(userId)
            if (reponse.isSuccessful) {
                val body = reponse.body()
                body?.let {
                    MyLog.e("chungnew",it[0].user_id)
                }
            }
        }
    }
}