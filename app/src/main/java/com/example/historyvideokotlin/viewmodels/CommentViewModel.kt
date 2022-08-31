package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CommentViewModel(application: Application) : BaseViewModel(application) {

    var commentList = MutableLiveData<List<Comment>>()
    var userList = MutableLiveData<ArrayList<User>>()
    var videoRepository = VideoRepository()
    var userRepository = UserRepository()
    var disposable: Disposable? = null

    fun getCommentData(videoId: String) {
        getComment(videoId)
    }

    private fun getComment(videoId: String) {
        disposable =
            videoRepository.getComment(videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribe(
                    { data ->
                        data.let {
                            commentList.value = it
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

    fun postComment(userId: String, videoId: String, content: String) {
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

    fun updateCommentCountVideo(videoId: String) {
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