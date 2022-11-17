package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import retrofit2.Response

class KtorQuizRepository(val apiService: KtorAPIService) {

    suspend fun getTheme(): List<Theme> = coroutineScope {
        withContext(IO) {
            val response = apiService.getTheme()
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getRandomQuiz(): List<Quiz> = coroutineScope {
        withContext(IO) {
            val response = apiService.getRandomQuiz()
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getQuizByTheme(themeInt: Int): List<Quiz> = coroutineScope {
        withContext(IO) {
            val response = apiService.getQuizByTheme(themeInt)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun createTheme(theme: Theme): CreateResponse = coroutineScope {
        withContext(IO) {
            val response: Response<CreateResponse> = apiService.createTheme(theme)
            if (response.isSuccessful) {
                val body = response.body()!!
                CreateResponse(body.isSuccess, body.data)
            } else {
                CreateResponse(false, "error")
            }
        }
    }

    suspend fun createQuiz(quiz: Quiz): CreateResponse = coroutineScope {
        withContext(IO) {
            val response: Response<CreateResponse> = apiService.createQuiz(quiz)
            if (response.isSuccessful) {
                val body = response.body()!!
                CreateResponse(body.isSuccess, body.data)
            } else {
                CreateResponse(false, "error")
            }
        }
    }

    suspend fun updateTheme(theme: Theme): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response: Response<UpdateResponse> = apiService.updateTheme(theme)
            if (response.isSuccessful) {
                val body = response.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }

    suspend fun updateQuiz(quiz: Quiz): UpdateResponse = coroutineScope {
        withContext(IO) {
            val response: Response<UpdateResponse> = apiService.updateQuiz(quiz)
            if (response.isSuccessful) {
                val body = response.body()!!
                UpdateResponse(body.isSuccess, body.data)
            } else {
                UpdateResponse(false, "error")
            }
        }
    }

    suspend fun deleteTheme(themeInt: Int): DeleteResponse = coroutineScope {
        withContext(IO) {
            val response: Response<DeleteResponse> = apiService.deleteTheme(themeInt)
            if (response.isSuccessful) {
                val body = response.body()!!
                DeleteResponse(body.isSuccess, body.data)
            } else {
                DeleteResponse(false, "error")
            }
        }
    }

    suspend fun deleteQuiz(quizInt: Int): DeleteResponse = coroutineScope {
        withContext(IO) {
            val response: Response<DeleteResponse> = apiService.deleteQuiz(quizInt)
            if (response.isSuccessful) {
                val body = response.body()!!
                DeleteResponse(body.isSuccess, body.data)
            } else {
                DeleteResponse(false, "error")
            }
        }
    }
}
