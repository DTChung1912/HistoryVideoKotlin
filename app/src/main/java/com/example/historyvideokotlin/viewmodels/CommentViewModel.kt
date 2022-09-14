package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CommentViewModel(application: Application) : BaseViewModel(application) {

    var commentList = MutableLiveData<List<Comment>>()
    var userList = MutableLiveData<ArrayList<User>>()
    var videoRepository = VideoRepository()
    var userRepository = UserRepository()
    var disposable: Disposable? = null

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    fun getCommentData(videoId: Int) {
        getComment(videoId)
    }

    private fun getComment(videoId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.getComment(videoId)
            }.onSuccess {
                commentList.value = it
            }.onFailure {
            }
        }
    }

     fun updateCommentLike(commentId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateCommentLike(commentId)
            }.onSuccess {
                MyLog.e("updateCommentLike",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateCommentLike",it.message)
            }
        }
    }

    fun updateCommentDislike(commentId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateCommentDislike(commentId)
            }.onSuccess {
                MyLog.e("updateCommentDislike",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateCommentDislike",it.message)
            }
        }
    }

    fun updateCommentLikeCancel(commentId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateCommentLikeCancel(commentId)
            }.onSuccess {
                MyLog.e("updateCommentLikeCancel",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateCommentLikeCancel",it.message)
            }
        }
    }

    fun updateCommentDislikeCancel(commentId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateCommentDislikeCancel(commentId)
            }.onSuccess {
                MyLog.e("updateCommentDislikeCancel",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateCommentDislikeCancel",it.message)
            }
        }
    }

    fun updateCommentReply(commentId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateCommentReply(commentId)
            }.onSuccess {
                MyLog.e("updateCommentReply",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateCommentReply",it.message)
            }
        }
    }

//    private fun getComment(videoId: Int) {
//        disposable =
//            videoRepository.getComment(videoId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { loadingLiveData.postValue(true) }
//                .doAfterTerminate { loadingLiveData.postValue(false) }
//                .subscribe(
//                    { data ->
//                        data.let {
//                            commentList.value = it
//                        }
//                    },
//                    { error -> MyLog.e("this", error.message.toString()) }
//                )
//    }

    fun updateLikeCountComment(commentId: String) {
        disposable =
            videoRepository.updateLikeCountComment(commentId, 1)
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

    fun updateDisikeCountComment(commentId: String) {
        disposable =
            videoRepository.updateDisikeCountComment(commentId, 1)
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

    fun updateReplyCountComment(commentId: String) {
        disposable =
            videoRepository.updateReplyCountComment(commentId, 1)
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

    fun postComment(userId: String, videoId: Int, content: String) {
        disposable = videoRepository.postComment(userId, videoId, content)
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

    fun updateCommentCountVideo(videoId: Int) {
        disposable =
            videoRepository.updateCommentCountVideo(videoId, 1)
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