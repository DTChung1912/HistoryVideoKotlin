package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.network.KtorAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class KtorQuizRepository(val apiService: KtorAPIService) {

    suspend fun getTheme(): List<Theme> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getTheme()
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getRandomQuiz(): List<Quiz> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getRandomQuiz()
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }

    suspend fun getQuizByTheme(themeInt: Int): List<Quiz> = coroutineScope {
        withContext(Dispatchers.IO) {
            val response = apiService.getQuizByTheme(themeInt)
            if (response.isSuccessful) {
                response.body().orEmpty()
            } else {
                listOf()
            }
        }
    }
}