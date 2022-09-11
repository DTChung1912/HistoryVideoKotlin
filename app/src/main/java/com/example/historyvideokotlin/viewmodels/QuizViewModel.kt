package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.QuizRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : BaseViewModel(application) {
    var themeList = MutableLiveData<List<Theme>>()
    var quizList = MutableLiveData<List<Quiz>>()

    var quizRepository = QuizRepository()
    var disposable = CompositeDisposable()

    val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    fun getThemeData() {
        getTheme()
    }

    private fun getTheme() {
        viewModelScope.launch {
            runCatching {
                loadingLiveData.postValue(true)
                ktorQuizRepository.getTheme()
            }.onSuccess {
                loadingLiveData.postValue(false)
                themeList.value = it
            }.onFailure {
                loadingLiveData.postValue(false)
            }
        }
    }

//    private fun getTheme() {
//        disposable.add(
//            quizRepository.getTheme()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { loadingLiveData.postValue(true) }
//                .doAfterTerminate { loadingLiveData.postValue(false) }
//                .subscribeWith(object : DisposableSingleObserver<List<Theme>>() {
//                    override fun onSuccess(t: List<Theme>) {
//                        themeList.value = t
//                        MyLog.e("chung", "Ok")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        MyLog.e("chung", e.message.toString())
//                    }
//
//                })
//        )
//    }


}