package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.AnswerModel
import com.example.historyvideokotlin.model.AnswerStatus
import com.example.historyvideokotlin.model.Quiz

class QuestionViewModel(application: Application) : BaseViewModel(application) {
    private val _quiz = MutableLiveData<Quiz>()
    val quiz: LiveData<Quiz> get() = _quiz

    private val _ansA = MutableLiveData("")
    val ansA: LiveData<String> get() = _ansA

    private val _ansB = MutableLiveData("")
    val ansB: LiveData<String> get() = _ansB

    private val _ansC = MutableLiveData("")
    val ansC: LiveData<String> get() = _ansC

    private val _ansD = MutableLiveData("")
    val ansD: LiveData<String> get() = _ansD

    private val _answer = MutableLiveData<AnswerModel>()
    val answer: LiveData<AnswerModel> get() = _answer

    fun setQuiz(quiz: Quiz) {
        _quiz.value = quiz

        val answerList = listOf(quiz.correct, quiz.incorrect_1, quiz.incorrect_2, quiz.incorrect_3)
        val ramdomAnswerList = mutableListOf<String>()
        while (ramdomAnswerList.size < 4) {
            val i = answerList.random()
            if (!ramdomAnswerList.contains(i)) {
                ramdomAnswerList.add(i)
            }
        }
        _ansA.value = ramdomAnswerList[0]
        _ansB.value = ramdomAnswerList[1]
        _ansC.value = ramdomAnswerList[2]
        _ansD.value = ramdomAnswerList[3]

        val answerModel = AnswerModel(
            AnswerStatus(ramdomAnswerList[0]),
            AnswerStatus(ramdomAnswerList[1]),
            AnswerStatus(ramdomAnswerList[2]),
            AnswerStatus(ramdomAnswerList[3])
        )

        _answer.value = answerModel
    }
}
