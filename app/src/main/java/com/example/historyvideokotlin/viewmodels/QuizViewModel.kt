package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.Repository.QuizRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.PostTheme
import com.example.historyvideokotlin.model.Quiz
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class QuizViewModel(application: Application) : BaseViewModel(application) {
    var themeList = MutableLiveData<List<PostTheme>>()
    var quizList = MutableLiveData<List<Quiz>>()

    var quizRepository = QuizRepository()
    var disposable = CompositeDisposable()

    fun getThemeData() {
        getTheme()
    }

    private fun getTheme() {
        disposable.add(
            quizRepository.getTheme()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PostTheme>>() {
                    override fun onSuccess(t: List<PostTheme>) {
                        themeList.value = t
                        Log.e("chung", "Ok")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("chung", e.message.toString())
                    }

                })
        )
    }


}