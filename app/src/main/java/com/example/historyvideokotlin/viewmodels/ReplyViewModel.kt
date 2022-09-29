package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.Reply
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ReplyViewModel(application: Application) : BaseViewModel(application) {

    var replyList = MutableLiveData<List<Reply>>()
    var videoRepository = VideoRepository()
    var disposable: Disposable? = null

    val ktorVideoRepository = application.repositoryProvider.ktorVideoRepository

    fun getReplyData(commentId: Int) {
        getReply(commentId)
    }

    private fun getReply(commentId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.getReply(commentId)
            }.onSuccess {
                replyList.value = it
            }.onFailure {
                MyLog.e("getReply: " ,it.message)
            }
        }
    }

    fun updateReplyLike(replyId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateReplyLike(replyId)
            }.onSuccess {
                MyLog.e("updateReplyLike: " ,it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateReplyLike: " ,it.message)
            }
        }
    }

    fun updateReplyLikeCancel(replyId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateReplyLikeCancel(replyId)
            }.onSuccess {
                MyLog.e("updateReplyLikeCancel: " ,it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateReplyLikeCancel: " ,it.message)
            }
        }
    }

    fun updateReplyDislike(replyId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateReplyDislike(replyId)
            }.onSuccess {
                MyLog.e("updateReplyDislike: " ,it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateReplyDislike: " ,it.message)
            }
        }
    }

    fun updateReplyDislikeCancel(replyId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorVideoRepository.updateReplyDislikeCancel(replyId)
            }.onSuccess {
                MyLog.e("updateReplyDislikeCancel: " ,it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updateReplyDislikeCancel: " ,it.message)
            }
        }
    }

//    private fun getReply(commentId: String) {
//        disposable = videoRepository.getReply(commentId)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe { showLoading() }
//            .doAfterTerminate { hideLoading() }
//            .subscribe(
//                { data ->
//                    data.let {
//                        replyList.value = it
//                    }
//                },
//                { error -> MyLog.e("this", error.message.toString()) }
//            )
//    }

    fun updateLikeCountComment(commentId: String) {
        disposable =
            videoRepository.updateLikeCountComment(commentId, 1)
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

    fun updateDisikeCountComment(commentId: String) {
        disposable =
            videoRepository.updateDisikeCountComment(commentId, 1)
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

    fun updateReplyCountComment(commentId: String) {
        disposable =
            videoRepository.updateReplyCountComment(commentId, 1)
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

    fun updateLikeCountReply(replyId: String) {
        disposable =
            videoRepository.updateLikeCountReply(replyId, 1)
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

    fun updateDislikeCountReply(replyId: String) {
        disposable =
            videoRepository.updateDislikeCountReply(replyId, 1)
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

    fun postReply(userId: String, partnerName: String, commentId: String, content: String) {

        disposable = videoRepository.postReply(userId, partnerName, commentId, content)
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