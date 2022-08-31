package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.Reply
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ReplyViewModel(application: Application) : BaseViewModel(application) {

    var replyList = MutableLiveData<List<Reply>>()
    var videoRepository = VideoRepository()
    var disposable: Disposable? = null

    fun getReplyData(commentId: String) {
        getReply(commentId)
    }

    private fun getReply(commentId: String) {
        disposable = videoRepository.getReply(commentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingLiveData.postValue(true) }
            .doAfterTerminate { loadingLiveData.postValue(false) }
            .subscribe(
                { data ->
                    data.let {
                        replyList.value = it
                    }
                },
                { error -> MyLog.e("this", error.message.toString()) }
            )
    }

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

    fun updateLikeCountReply(replyId: String) {
        disposable =
            videoRepository.updateLikeCountReply(replyId, 1)
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

    fun updateDislikeCountReply(replyId: String) {
        disposable =
            videoRepository.updateDislikeCountReply(replyId, 1)
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

    fun postReply(userId: String, partnerName: String, commentId: String, content: String) {

        disposable = videoRepository.postReply(userId, partnerName, commentId, content)
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