package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.PostRepository
import com.example.historyvideokotlin.repository.QuizRepository
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : BaseViewModel(application) {
    private var userRepository: UserRepository
    private var postRepository: PostRepository
    private var videoRepository: VideoRepository
    private var quizRepository: QuizRepository
    private val isShowBottomMenu = MutableLiveData<Boolean>()
    val disposable = CompositeDisposable()
    var disposable2: Disposable? = null


    fun getIsShowBottomMenu(): MutableLiveData<Boolean> {
        return isShowBottomMenu
    }

    fun getInitData() {
    }

    private fun getUserData() {

    }

    private fun getPostData() {

    }

    private fun getQuizData() {

    }

    private fun getVideoData() {

    }

    init {
        userRepository = UserRepository()
        postRepository = PostRepository()
        videoRepository = VideoRepository()
        quizRepository = QuizRepository()
    }

    fun postUser(userId: String, name: String, email: String) {
        disposable2 = userRepository.postUser2(userId,name, email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingLiveData.postValue(true) }
            .doAfterTerminate { loadingLiveData.postValue(false) }
            .subscribe(
                { result -> MyLog.e("chung", result.toString()) },
                { error -> MyLog.e("this", error.message.toString()) },
                { Log.i("TAG", "Login Completed") }
            )
    }


}