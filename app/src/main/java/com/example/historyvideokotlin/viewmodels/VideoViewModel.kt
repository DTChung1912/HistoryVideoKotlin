package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
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

    private val _isLoadFail = MutableLiveData(false)
    val isLoadFail: LiveData<Boolean> get() = _isLoadFail

    fun getVideoData() {
//        getVideo()
        getVideoList()
//        getComment()
//        getMyVideo()
    }

    private fun getVideoList() {
        _isLoadFail.value = false
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorVideoRepository.getVideoList()
            }.onSuccess {
                hideLoading()
                videoList.value = it
                _isLoadFail.value = false
            }.onFailure {
                MyLog.e("getVideoList", it.message)
                hideLoading()
                _isLoadFail.value = true
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
}
