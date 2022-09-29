package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NextVideoViewModel(application: Application) : BaseViewModel(application) {
    var videoList = MutableLiveData<List<Video>>()
    private var videoRepository = VideoRepository()
    private var userRepository = UserRepository()
    private var disposable: Disposable? = null
    private var disposable2 = CompositeDisposable()
    var userId = HistoryUserManager.instance.UserId()

    fun getVideoData() {
        getVideo()
    }

    private fun getVideo() {
        disposable2.add(
            videoRepository.getVideo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeWith(object : DisposableSingleObserver<List<Video>>() {
                    override fun onSuccess(t: List<Video>) {
                        videoList.value = t
                        MyLog.e("chung", "Ok")
                    }

                    override fun onError(e: Throwable) {
                        MyLog.e("chung", e.message.toString())
                    }

                })
        )
    }

    fun updateLikeVideo(videoId: Int, isLike: Int) {
        disposable =
            userRepository
                .updateLikeMyVideo(userId, videoId, isLike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") }
                )
    }

    fun updateViewedVideo(videoId: Int, isView: Int) {
        disposable =
            userRepository
                .updateViewedMyVideo(userId, videoId, isView)
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })

    }

    fun updateLaterVideo( videoId: Int, isLater: Int) {
        disposable =
            userRepository
                .updateLaterMyVideo(userId, videoId, isLater)
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateDownloadVideo( videoId: Int, isDownload: Int) {
        disposable =
            userRepository
                .updateDownloadMyVideo(userId, videoId, isDownload)
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateShareVideo( videoId: Int, isShare: Int) {
        disposable =
            userRepository
                .updateShareMyVideo(userId, videoId, isShare)
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateDontCareVideo(videoId: Int, isDontCared: Int) {
        disposable =
            userRepository
                .updateDontCareMyVideo(userId, videoId, isDontCared)
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }
}