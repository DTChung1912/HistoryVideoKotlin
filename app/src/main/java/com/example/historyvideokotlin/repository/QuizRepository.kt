package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.model.Quiz
import io.reactivex.Single

class QuizRepository : BaseRepository() {
    fun getTheme() : Single<List<Theme>> {
        return apiService?.getTheme()!!
    }

    fun getQuiz() : Single<List<Quiz>> {
        return apiService?.getQuiz()!!
    }
}