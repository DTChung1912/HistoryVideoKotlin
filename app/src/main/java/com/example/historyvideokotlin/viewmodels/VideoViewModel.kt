package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : BaseViewModel(application) {
    var videoList = MutableLiveData<List<Video>>()
    private var videoRepository = VideoRepository()
    private var userRepository = UserRepository()
    private var disposable: Disposable? = null
    private var disposable2 = CompositeDisposable()
    var userId = HistoryUserManager.FUid()

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    fun getVideoData() {
        getVideo()
        getVideo2()
        getComment()
        getMyVideo()
    }

    private fun getVideo() {
        disposable2.add(
            videoRepository.getVideo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
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

    private fun getVideo2() {
        var videoList = mutableListOf<Video>()
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.getVideo()
            }.onSuccess {
                videoList.addAll(it)
                MyLog.e("ktorVideoRepositoryvideo", "" + videoList.size + " ")
            }.onFailure {
            }
        }
    }

    private fun getComment() {
        var commentList = mutableListOf<Comment>()
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.getComment(1)
            }.onSuccess {
                commentList.addAll(it)
                MyLog.e("ktorVideoRepositoryCommnent", "" + commentList.size + " ")
            }.onFailure {
            }
        }
    }

    private fun getMyVideo() {
        var myVideoList = mutableListOf<MyVideo>()
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.getVideo("EL6HKxq15Oey5F9wmrnMn2CNd032",1)
            }.onSuccess {
                myVideoList.addAll(it)
                MyLog.e("ktorVideoRepositoryMyVIdeo", "" + myVideoList.size + " ")
            }.onFailure {
            }
        }
    }


//    private fun register() {
//        val user : User = User(
//            user_id = "aaaaaa",
//            user_name = "chung",
//            user_image = "",
//            email = "",
//            birthday = "",
//            phone_number = "",
//            address = "",
//            last_active = "",
//            account_type_id = 1
//        )
//        var userList = mutableListOf<User>()
//        viewModelScope.launch {
//            runCatching {
//                ktorVideoRepository.register(user)
//            }.onSuccess {
//                userList.addAll(it)
//                MyLog.e("ktorVideoRepositoryvideo", "" + userList.size + " ")
//            }.onFailure {
//            }
//        }
//    }

    fun updateLikeMyVideo(videoId: String, isLike: Int) {
        disposable =
            userRepository
                .updateLikeMyVideo(userId, videoId, isLike)
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

    fun updateViewedMyVideo(videoId: String, isView: Int) {
        disposable =
            userRepository
                .updateViewedMyVideo(userId, videoId, isView)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })

    }

    fun updateLaterVideo(videoId: String, isLater: Int) {
        disposable =
            userRepository
                .updateLaterMyVideo(userId, videoId, isLater)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateDownloadMyVideo(videoId: String, isDownload: Int) {
        disposable =
            userRepository
                .updateDownloadMyVideo(userId, videoId, isDownload)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateShareVideo(videoId: String, isShare: Int) {
        disposable =
            userRepository
                .updateShareMyVideo(userId, videoId, isShare)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateDontCareVideo(videoId: String, isDontCared: Int) {
        disposable =
            userRepository
                .updateDontCareMyVideo(userId, videoId, isDontCared)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }
}