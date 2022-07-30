package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.Repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.PostPerson
import com.example.historyvideokotlin.model.Video
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class VideoViewModel(application: Application) : BaseViewModel(application) {
    var videoList = MutableLiveData<List<Video>>()
    var videoRepository = VideoRepository()
    var disposable = CompositeDisposable()

    fun getVideoData() {
        getVideo()
    }

    private fun getVideo() {
        disposable.add(
            videoRepository.getVideo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Video>>() {
                    override fun onSuccess(t: List<Video>) {
                        videoList.value = t
                        Log.e("chung" , "Ok")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("chung" , e.message.toString())
                    }

                })
        )
    }
}