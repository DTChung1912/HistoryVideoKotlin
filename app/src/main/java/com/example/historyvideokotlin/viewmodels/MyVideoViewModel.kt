package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.model.MyVideoModel
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MyVideoViewModel(application: Application) : BaseViewModel(application) {

    private var disposable: Disposable? = null
    private var userRepository = UserRepository()

    val video = MutableLiveData<Video>()
    val videoList = MutableLiveData<List<MyVideoModel>>()
    val myVideoResponse = MutableLiveData<List<MyVideoModel>>()

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository
    val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    val userId = HistoryUserManager.instance.UserId()
    val downloadVideoList = MutableLiveData<List<DownloadVideo>>()

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    private val _isLoadFail = MutableLiveData(false)
    val isLoadFail: LiveData<Boolean> get() = _isLoadFail

    fun getMyVideoList(userId: String) {
        viewModelScope.launch {
            runCatching {
                ktorUserRepository.getMyVideoList(userId)
            }.onSuccess {
                videoList.value = it
            }.onFailure {
                MyLog.e("getMyVideoList ", it.message)
            }
        }
    }

    fun getMyVideoListType(type: Int) {
        viewModelScope.launch {
            runCatching {
                ktorUserRepository.getMyVideoList(userId, type)
            }.onSuccess {
                if (it.isEmpty()) {
                    _isEmpty.value = true
                } else {
                    _isEmpty.value = false
                }
                myVideoResponse.value = it
            }.onFailure {
                MyLog.e("getMyVideoList ", it.message)
            }
        }
    }

    fun deleteViewMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteViewMyVideo(myVideoId, 1)
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

    fun deleteLikeMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteLikeMyVideo(myVideoId, 1)
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

    fun deleteLaterMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteLaterMyVideo(myVideoId, 1)
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

    fun deleteDownloadMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteDownloadMyVideo(myVideoId, 1)
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
}
