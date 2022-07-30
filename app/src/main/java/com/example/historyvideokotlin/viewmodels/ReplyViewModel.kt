package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.Repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.Reply
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ReplyViewModel(application: Application) : BaseViewModel(application) {

    var replyList = MutableLiveData<List<Reply>>()
    var videoRepository = VideoRepository()
    var disposable: Disposable? = null

    fun getReplyData(commentId : String) {
        getReply(commentId)
    }

    private fun getReply(commentId: String) {
        disposable = videoRepository.getReply(commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {data ->
                        data.let {
                            replyList.value = it
                        }
                    },
                    { error -> Log.e("this", error.message.toString()) }
                )
    }

    fun updateCommnentDislikeCount () {

    }
}