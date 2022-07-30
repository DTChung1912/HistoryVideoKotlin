package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.Repository.UserRepository
import com.example.historyvideokotlin.Repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.model.Video
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
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
                .subscribe(
                    { data ->
                        data.let {
                            commentList.value = it
                        }
                    },
                    { error -> Log.e("this", error.message.toString()) }
                )
    }

}