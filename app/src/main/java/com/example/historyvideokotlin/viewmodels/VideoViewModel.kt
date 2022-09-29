package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.data.VideoDatabase
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoViewModel(application: Application) : BaseViewModel(application) {
    var videoList = MutableLiveData<List<Video>>()
    var durationList = MutableLiveData<List<String>>()
    private var videoRepository = VideoRepository()
    private var userRepository = UserRepository()
    private var disposable: Disposable? = null
    private var disposable2 = CompositeDisposable()
    var userId = HistoryUserManager.instance.UserId()

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    fun getVideoData() {
//        getVideo()
        getVideoList()
        getComment()
//        getMyVideo()
    }

//    private fun getVideo() {
//        disposable2.add(
//            videoRepository.getVideo()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { showLoading() }
//                .doAfterTerminate { hideLoading() }
//                .subscribeWith(object : DisposableSingleObserver<List<Video>>() {
//                    override fun onSuccess(t: List<Video>) {
//                        videoList.value = t
//                        MyLog.e("chung", "Ok")
//                    }
//
//                    override fun onError(e: Throwable) {
//                        MyLog.e("chung", e.message.toString())
//                    }
//                })
//        )
//    }

//    fun getTest(database: VideoDatabase) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val a = database.videoDao().getListVideo()
//            MyLog.e("getDownloadVideo-------------",a.toString())
//        }
//    }

    private fun getVideoList() {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorVideoRepository.getVideoList()
            }.onSuccess {
                hideLoading()
                videoList.value = it
            }.onFailure {
                hideLoading()
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

    fun updateVideoView(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateVideoView(videoId)
            }.onSuccess {
                MyLog.e("updateVideoView", it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateVideoView", it.message.toString())
            }
        }
    }

    fun updateVideoLike(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateVideoLike(videoId)
            }.onSuccess {
                MyLog.e("updateVideoLike", it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateVideoLike", it.message.toString())
            }
        }
    }

    fun updateVideoLikeCancel(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateVideoLikeCancel(videoId)
            }.onSuccess {
                MyLog.e("updateVideoLikeCancel", it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateVideoLikeCancel", it.message.toString())
            }
        }
    }

    fun updateVideoDislike(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateVideoDislike(videoId)
            }.onSuccess {
                MyLog.e("updateVideoDislike", it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateVideoDislike", it.message.toString())
            }
        }
    }

    fun updateVideoDislikeCancel(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateVideoDislikeCancel(videoId)
            }.onSuccess {
                MyLog.e("updateVideoDislikeCancel", it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateVideoDislikeCancel", it.message.toString())
            }
        }
    }

    fun updateVideoDownload(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateVideoDownload(videoId)
            }.onSuccess {
                MyLog.e("updateVideoDownload", it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateVideoDownload", it.message.toString())
            }
        }
    }

    fun updateVideoComment(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateVideoComment(videoId)
            }.onSuccess {
                MyLog.e("updateVideoComment", it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateVideoComment", it.message.toString())
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

    fun updateLikeMyVideo(videoId: Int, isLike: Int) {
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

    fun updateViewedMyVideo(videoId: Int, isView: Int) {
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

    fun updateLaterVideo(videoId: Int, isLater: Int) {
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

    fun updateDownloadMyVideo(videoId: Int, isDownload: Int) {
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

    fun updateShareVideo(videoId: Int, isShare: Int) {
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