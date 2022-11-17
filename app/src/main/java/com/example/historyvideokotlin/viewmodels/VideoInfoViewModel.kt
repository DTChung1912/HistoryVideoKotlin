package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.data.HistoryDatabase
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.repository.DownloadVideoRepository
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class VideoInfoViewModel(application: Application) : BaseViewModel(application) {

    private var myVideoId = 0
    private var disposable: Disposable? = null
    private var disposable2 = CompositeDisposable()
    private var videoRepository = VideoRepository()
    private var userRepository = UserRepository()
    var userId = HistoryUserManager.instance.UserId()

    private val _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> get() = _isLiked

    private val _isDisliked = MutableLiveData(false)
    val isDisliked: LiveData<Boolean> get() = _isDisliked

    private val _isDownload = MutableLiveData(false)
    val isDownload: LiveData<Boolean> get() = _isDownload

    private val _isLater = MutableLiveData(false)
    val isLater: LiveData<Boolean> get() = _isLater

    private val _video = MutableLiveData<Video>()
    val video: LiveData<Video> get() = _video

    private val _likeCount = MutableLiveData(0)
    val likeCount: LiveData<Int> get() = _likeCount

    private val _dislikeCount = MutableLiveData(0)
    val dislikeCount: LiveData<Int> get() = _dislikeCount

    private val downloadVideoRepository: DownloadVideoRepository
    val ktorUserRepository = application.repositoryProvider.ktorUserRepository
    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    init {
        val videoDao = HistoryDatabase.getDatabase(application).videoDao()
        downloadVideoRepository = DownloadVideoRepository(videoDao)
    }

    val myVideoStatus = MediatorLiveData<MyVideoStatus>()

    fun setVideo(video: Video) {
        _video.value = video
        _likeCount.value = video.like_count
        _dislikeCount.value = video.dislike_count
        getMyVideo(video.video_id)
    }

    private fun getMyVideo(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getMyVideo(userId, videoId)
            }.onSuccess {
                myVideoStatus.value = it
                myVideoId = it.my_video_id!!
                when (it.isLike) {
                    0 -> {
                        _isLiked.value = false
                        _isDisliked.value = false
                    }
                    1 -> {
                        _isLiked.value = true
                        _isDisliked.value = false
                    }
                    2 -> {
                        _isLiked.value = false
                        _isDisliked.value = true
                    }
                }
                _isDownload.value = if (it.isDownload == 1) true else false
                _isLater.value = if (it.isLater == 1) true else false
                hideLoading()
            }.onFailure {
                hideLoading()
            }
        }
    }

    fun downloadVideo(downloadVideo: DownloadVideo) {
        viewModelScope.launch(IO) {
            downloadVideoRepository.insertVideo(downloadVideo)
        }
    }

    fun setDownload(isDownload: Boolean) {
        _isDownload.value = isDownload
        updateMyVideoDownload(if (isDownload) 1 else 0)
    }

    fun setLiked(videoId: Int) {
        _isLiked.value = true
        if (_isDisliked.value!!) {
            _isDisliked.value = false
            updateVideoDislikeCancel(videoId)
        }
        updateMyVideoLike(1)
        updateVideoLike(videoId)
    }

    fun setDisliked(videoId: Int) {
        _isDisliked.value = true
        if (_isDisliked.value!!) {
            _isLiked.value = false
            updateVideoLikeCancel(videoId)
        }
        updateMyVideoLike(2)
        updateVideoDislike(videoId)
    }

    fun cancelLike(videoId: Int) {
        _isDisliked.value = false
        _isLiked.value = false
        updateMyVideoLike(0)
        updateVideoLikeCancel(videoId)
    }

    fun cancelDislike(videoId: Int) {
        _isDisliked.value = false
        _isLiked.value = false
        updateMyVideoLike(0)
        updateVideoDislikeCancel(videoId)
    }

    fun setLatered(isLatered: Boolean) {
        _isLater.value = isLatered
        updateMyVideoLater(if (isLatered) 1 else 0)
    }

    fun getTabTitleIds(): List<Int> {
        val titleIds = mutableListOf<Int>()
        titleIds.run {
            add(R.string.title_video_tab_next_video)
            add(R.string.title_video_tab_commet)
        }
        return titleIds
    }

    fun updateMyVideoLike(isLike: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorUserRepository.updateMyVideoLike(myVideoId, isLike))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateMyVideoLike", it.data)
                }
            }
        }
    }

    fun updateMyVideoDownload(isDownload: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorUserRepository.updateMyVideoDownload(myVideoId, isDownload))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                MyLog.e("updateMyVideoDownload", it.data)
            }
        }
    }

    fun updateMyVideoLater(isLater: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorUserRepository.updateMyVideoLater(myVideoId, isLater))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateMyVideoLater", it.data)
                }
            }
        }
    }

    fun updateVideoLike(videoId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateVideoLike(videoId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateVideoLike", it.data.toString())
                    _likeCount.value = it.data
                }
            }
        }
    }

    fun updateVideoDislike(videoId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateVideoDislike(videoId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateVideoDislike", it.data.toString())
                    _dislikeCount.value = it.data
                }
            }
        }
    }

    fun updateVideoLikeCancel(videoId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateVideoLikeCancel(videoId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateVideoLikeCancel", it.data.toString())
                    _likeCount.value = it.data
                }
            }
        }
    }

    fun updateVideoDislikeCancel(videoId: Int) {
        viewModelScope.launch {
            val updateFlow = flow {
                emit(ktorVideoRepository.updateVideoDislikeCancel(videoId))
            }.onStart {
            }.onCompletion {
            }
            updateFlow.collect {
                it.let {
                    MyLog.e("updateVideoDislikeCancel", it.data.toString())
                    _dislikeCount.value = it.data
                }
            }
        }
    }
}
