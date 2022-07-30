package com.example.historyvideokotlin.Repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.PostTheme
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.Video
import io.reactivex.Single

class QuizRepository : BaseRepository() {
    fun getTheme() : Single<List<PostTheme>> {
        return apiService?.getTheme()!!
    }

    fun getQuiz() : Single<List<Quiz>> {
        return apiService?.getQuiz()!!
    }
}