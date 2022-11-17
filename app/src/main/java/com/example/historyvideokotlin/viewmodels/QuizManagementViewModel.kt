package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QuizManagementViewModel(application: Application) : BaseViewModel(application) {
    val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    var quizList = MutableLiveData<List<Quiz>>()

    fun getQuizList() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorQuizRepository.getRandomQuiz()
            }.onSuccess {
                hideLoading()
                quizList.value = it
            }.onFailure {
                MyLog.e("getRandomQuiz", it.message)
                hideLoading()
            }
        }
    }

    fun deleteQuiz(quizId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorQuizRepository.deleteQuiz(quizId))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("deleteQuiz", it.data.toString())
                }
            }
        }
    }
}
