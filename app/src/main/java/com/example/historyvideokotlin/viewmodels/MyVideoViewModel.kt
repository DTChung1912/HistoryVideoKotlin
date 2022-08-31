package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MyVideoViewModel(application: Application) : BaseViewModel(application) {

    private var disposable: Disposable? = null
    private var userRepository = UserRepository()

    fun deleteViewMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteViewMyVideo(myVideoId, 1)
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

    fun deleteLikeMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteLikeMyVideo(myVideoId, 1)
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

    fun deleteLaterMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteLaterMyVideo(myVideoId, 1)
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

    fun deleteDownloadMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteDownloadMyVideo(myVideoId, 1)
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