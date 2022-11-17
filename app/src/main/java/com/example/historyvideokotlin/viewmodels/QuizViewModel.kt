package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.repository.QuizRepository
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : BaseViewModel(application) {
    var themeList = MutableLiveData<List<Theme>>()

    var quizRepository = QuizRepository()
    var disposable = CompositeDisposable()

    val ktorQuizRepository = application.repositoryProvider.ktorQuizRepository

    private val _isLoadFail = MutableLiveData(false)
    val isLoadFail: LiveData<Boolean> get() = _isLoadFail

    fun getThemeData() {
        getTheme()
    }

    private fun getTheme() {
        _isLoadFail.value = false
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorQuizRepository.getTheme()
            }.onSuccess {
                hideLoading()
                themeList.value = it
                _isLoadFail.value = false
            }.onFailure {
                MyLog.e("getTheme", it.message)
                hideLoading()
                _isLoadFail.value = true
            }
        }
    }

//    private fun getTheme() {
//        disposable.add(
//            quizRepository.getTheme()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { showLoading() }
//                .doAfterTerminate { hideLoading() }
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
