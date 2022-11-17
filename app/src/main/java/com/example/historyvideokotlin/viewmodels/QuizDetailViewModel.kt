package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.repository.QuizRepository
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class QuizDetailViewModel(application: Application) : BaseViewModel(application) {
    private val quizRepository = QuizRepository()
    val quizList = MutableLiveData<List<Quiz>>()
    private val disposable = CompositeDisposable()

    val selecAnswerList = MutableLiveData<List<SelectAnswer>>()
    private val selectList = mutableListOf<SelectAnswer>()

    private val titleList = mutableListOf<String>()
    val titleAnswerList = MutableLiveData<List<String>>()

    val correctAnswerList = MutableLiveData<List<String>>()
    private val correctlist = mutableListOf<String>()

    private val checkList = mutableListOf<Int>()
    val checkAnswerList = MutableLiveData<List<Int>>()

    private val answerList = mutableListOf<AnswerModel>()
    val answerModelList = MutableLiveData<List<AnswerModel>>()

    val result = MutableLiveData<ResultCount>()

    private val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    init {
        answerList.clear()
        titleList.clear()
        checkList.clear()
        selectList.clear()
        for (i in 1..40) {
            answerList.add(AnswerModel())
            titleList.add("")
            checkList.add(AnswerType.NOT.ordinal)
            selectList.add(SelectAnswer(number = i))
        }
        answerModelList.value = answerList
        selecAnswerList.value = selectList
        checkAnswerList.value = checkList
        titleAnswerList.value = titleList
    }

    fun setCorrectList(quizList: List<Quiz>) {
        correctlist.clear()
        for (i in quizList) {
            correctlist.add(i.correct)
        }
        correctAnswerList.value = correctlist
    }

    fun getQuizData() {
        getRandomQuiz()
    }

    fun setResult() {
        val resultCount = ResultCount(40, 0, 0)
        for (i in checkList) {
            when (i) {
                AnswerType.NOT.ordinal -> {
                }
                AnswerType.CORRECT.ordinal -> {
                    resultCount.correctCount++
                    resultCount.notCount--
                }
                AnswerType.WRONG.ordinal -> {
                    resultCount.wrongCount++
                    resultCount.notCount--
                }
            }
        }
        MyLog.e("resultCount", resultCount.toString())
        result.value = resultCount
    }

    fun setAnswerModelList(title: String, position: Int) {
        val answerModel = answerList[position]
        when(title) {
            AnswerTitle.A.title -> {
                answerModel.ansA!!.status = AnswerType.WRONG.ordinal
                answerList.set(position, answerModel)
                answerModelList.value = answerList
            }
            AnswerTitle.B.title -> {
                answerModel.ansB!!.status = AnswerType.WRONG.ordinal
                answerList.set(position, answerModel)
                answerModelList.value = answerList
            }
            AnswerTitle.C.title -> {
                answerModel.ansC!!.status = AnswerType.WRONG.ordinal
                answerList.set(position, answerModel)
                answerModelList.value = answerList
            }
            AnswerTitle.D.title -> {
                answerModel.ansD!!.status = AnswerType.WRONG.ordinal
                answerList.set(position, answerModel)
                answerModelList.value = answerList
            }
        }

    }

    fun setTitleAnswer(title: String, position: Int) {
        titleList.set(position, title)
        titleAnswerList.value = titleList
    }

    fun setSelectAnswer(selectAnswer: SelectAnswer) {
        selectList.set(selectAnswer.number!! - 1, selectAnswer)
        selecAnswerList.value = selectList
    }

    fun checkAnswer(isCorrect: Boolean, position: Int) {
        if (isCorrect) {
            checkList.set(position, AnswerType.CORRECT.ordinal)
        } else {
            checkList.set(position, AnswerType.WRONG.ordinal)
        }
        checkAnswerList.value = checkList
    }

    private fun getRandomQuiz() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorQuizRepository.getRandomQuiz()
            }.onSuccess {
                quizList.value = it
                answerList.clear()
                for (quiz in it) {
                    val answerQuizList =
                        listOf(quiz.correct, quiz.incorrect_1, quiz.incorrect_2, quiz.incorrect_3)
                    val ramdomAnswerList = mutableListOf<String>()
                    val randomStatusList = mutableListOf<Int>()
                    while (ramdomAnswerList.size < 4) {
                        val i = answerQuizList.random()
                        if (!ramdomAnswerList.contains(i)) {
                            ramdomAnswerList.add(i)
                        }
                    }
                    for (j in 0 until ramdomAnswerList.size) {
                        if (ramdomAnswerList[j].equals(quiz.correct)) {
                            randomStatusList.add(AnswerType.CORRECT.ordinal)
                        } else {
                            randomStatusList.add(AnswerType.NOT.ordinal)
                        }
                    }
                    val answerModel = AnswerModel(
                        AnswerStatus(ramdomAnswerList[0], randomStatusList[0]),
                        AnswerStatus(ramdomAnswerList[1], randomStatusList[1]),
                        AnswerStatus(ramdomAnswerList[2], randomStatusList[2]),
                        AnswerStatus(ramdomAnswerList[3], randomStatusList[3])
                    )
                    answerList.add(answerModel)
                }
                answerModelList.value = answerList
                hideLoading()
            }.onFailure {
                hideLoading()
            }
        }
    }

    private fun getQuizByTheme(themeId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorQuizRepository.getQuizByTheme(themeId)
            }.onSuccess {
                quizList.value = it
            }.onFailure {
            }
        }
    }

    fun getQuiz(theme_id: Int) {
        disposable.add(
            quizRepository.getQuiz()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeWith(object : DisposableSingleObserver<List<Quiz>>() {
                    override fun onSuccess(t: List<Quiz>) {
                        quizList.value = t
                        MyLog.e("chung", "Ok")
                    }

                    override fun onError(e: Throwable) {
                        MyLog.e("chung", e.message.toString())
                    }
                })
        )
    }
}
