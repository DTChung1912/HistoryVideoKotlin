package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.QuizRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class QuizDetailViewModel(application: Application) : BaseViewModel(application) {
    val quizRepository = QuizRepository()
    val quizList = MutableLiveData<List<Quiz>>()
    val disposable = CompositeDisposable()

    val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    fun getQuizData() {
        getRandomQuiz()
    }

    private fun getRandomQuiz() {
        viewModelScope.launch {
            runCatching {
                ktorQuizRepository.getRandomQuiz()
            }.onSuccess {
                quizList.value = it
            }.onFailure {
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

//    private fun getQuiz() {
//        disposable.add(
//            quizRepository.getQuiz()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { loadingLiveData.postValue(true) }
//                .doAfterTerminate { loadingLiveData.postValue(false) }
//                .subscribeWith(object : DisposableSingleObserver<List<Quiz>>() {
//                    override fun onSuccess(t: List<Quiz>) {
//                        quizList.value = t
//                    }
//
//                    override fun onError(e: Throwable) {
//                        MyLog.e("chung",e.message.toString())
//                    }
//
//                })
//        )
//    }



    fun getQuiz(theme_id : Int) {
        disposable.add(
            quizRepository.getQuiz()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
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