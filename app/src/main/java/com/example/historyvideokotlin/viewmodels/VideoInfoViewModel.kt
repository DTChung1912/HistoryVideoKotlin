package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.repository.VideoRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class VideoInfoViewModel(application: Application) : BaseViewModel(application) {

    lateinit var isLiked: MutableLiveData<Boolean>
    private var disposable: Disposable? = null
    private var disposable2 = CompositeDisposable()
    private var videoRepository = VideoRepository()
    private var userRepository = UserRepository()
    var myVideoList = MutableLiveData<List<MyVideo>>()
    var userId = HistoryUserManager.FUid()

    fun setLiked(isliked: Boolean) {
        isLiked.value = isliked
    }

    fun getTabTitleIds(): List<Int> {
        val titleIds = mutableListOf<Int>()
        titleIds.run {
            add(R.string.title_video_tab_next_video)
            add(R.string.title_video_tab_commet)
        }
        return titleIds
    }

    fun getMyVideo(videoId: Int) {
        disposable2.add(
            userRepository.getMyVideo(userId, videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribe(
                    { data ->
                        data.let {
                            myVideoList.value = it
                        }
                    },
                    { error -> MyLog.e("this", error.message.toString()) }
                )
        )
    }

    fun updateLikeMyVideo(videoId: Int) {
        disposable =
            userRepository
                .updateLikeMyVideo(userId, videoId, 1)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") }
                )
    }

    fun updateViewedMyVideo(videoId: Int) {
        disposable =
            userRepository
                .updateViewedMyVideo(userId, videoId, 1)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })

    }

    fun updateLaterMyVideo(videoId: Int) {
        disposable =
            userRepository
                .updateLaterMyVideo(userId, videoId, 1)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateDownloadMyVideo(videoId: Int) {
        disposable =
            userRepository
                .updateDownloadMyVideo(userId, videoId, 1)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateShareMyVideo(videoId: Int) {
        disposable =
            userRepository
                .updateShareMyVideo(userId, videoId, 1)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateDontCareVideo(videoId: Int) {
        disposable =
            userRepository
                .updateDontCareMyVideo(userId, videoId, 1)
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") })
    }

    fun updateViewCountVideo(videoId: Int) {
        disposable =
            videoRepository.updateViewCountVideo(videoId, 1)
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

    fun updateLikeCountVideo(videoId: Int) {
        disposable =
            videoRepository.updateLikeCountVideo(videoId, 1)
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

    fun updateDislikeCountVideo(videoId: Int) {
        disposable =
            videoRepository.updateDislikeCountVideo(videoId, 1)
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

    fun updateLikeCancelVideo(videoId: Int) {
        disposable =
            videoRepository.updateLikeCancelVideo(videoId, 1)
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

    fun updateDislikeCancelVideo(videoId: Int) {
        disposable =
            videoRepository.updateDislikeCancelVideo(videoId, 1)
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

    fun deleteLikeMyVideo(myVideoId: Int) {
        disposable =
            userRepository.deleteLikeMyVideo(myVideoId, 1)
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