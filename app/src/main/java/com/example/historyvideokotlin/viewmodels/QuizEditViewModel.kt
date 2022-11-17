package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.utils.MyLog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QuizEditViewModel(application: Application) : BaseViewModel(application) {
    val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    private val _isUpdate = MutableLiveData(false)
    val isUpdate: LiveData<Boolean> get() = _isUpdate

    private val _quiz = MutableLiveData<Quiz>()
    val quiz: LiveData<Quiz> get() = _quiz

    val isUpdateComplete = MutableLiveData<Boolean>()
    val isAddComplete = MutableLiveData<Boolean>()

    fun setQuiz(quiz: Quiz) {
        _quiz.value = quiz
        _isUpdate.value = if (quiz.quiz_id == 0) false else true
    }

    fun createQuiz(quiz: Quiz) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorQuizRepository.createQuiz(quiz))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    isAddComplete.value = it.isSuccess
                    MyLog.e("createQuiz", it.data.toString())
                }
            }
        }
    }

    fun updateQuiz(quiz: Quiz) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorQuizRepository.updateQuiz(quiz))
            }.onStart {
                showLoading()
            }.onCompletion {
                hideLoading()
            }
            updateFlow.collect {
                it.let {
                    isUpdateComplete.value = it.isSuccess
                    MyLog.e("createQuiz", it.data.toString())
                }
            }
        }
    }
}
