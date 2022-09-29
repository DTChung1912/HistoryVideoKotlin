package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.PostRepository
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class PostDetailViewModel(application: Application) : BaseViewModel(application) {

    private var disposable: Disposable? = null
    private var disposable2 = CompositeDisposable()
    private var postRepository = PostRepository()
    private var userRepository = UserRepository()

    val ktorPostRepository = application.repositoryProvider.ktorPostRepository

    fun updatePostRead(postId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorPostRepository.updatePostRead(postId)
            }.onSuccess {
                MyLog.e("updatePostRead: ",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updatePostRead: ",it.message.toString())
            }
        }
    }

    fun updatePostRate(postId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorPostRepository.updatePostRate(postId)
            }.onSuccess {
                MyLog.e("updatePostRate: ",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updatePostRate: ",it.message.toString())
            }
        }
    }

    fun updateDownloadCountPost(postId: String, downloaded: Int) {
        disposable =
            postRepository.updateDownloadCountPost(postId, downloaded)
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") }
                )
    }

    fun updateRateCountPost(postId: String, rated: Int) {
        disposable =
            postRepository.updateRateCountPost(postId, rated)
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> MyLog.e("chung", result.toString()) },
                    { error -> MyLog.e("this", error.message.toString()) },
                    { Log.i("TAG", "Login Completed") }
                )
    }

//    fun updateReadCountPost(postId: String, read: Int) {
//        disposable =
//            postRepository.updateReadCountPost(postId, read)
//                .doOnSubscribe { showLoading() }
//                .doAfterTerminate { hideLoading() }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { result -> MyLog.e("chung", result.toString()) },
//                    { error -> MyLog.e("this", error.message.toString()) },
//                    { Log.i("TAG", "Login Completed") }
//                )
//    }
//
//    fun updateDownloadCountPost(postId: String, downloaded: Int) {
//        disposable =
//            postRepository.updateDownloadCountPost(postId, downloaded)
//                .doOnSubscribe { showLoading() }
//                .doAfterTerminate { hideLoading() }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { result -> MyLog.e("chung", result.toString()) },
//                    { error -> MyLog.e("this", error.message.toString()) },
//                    { Log.i("TAG", "Login Completed") }
//                )
//    }
//
//    fun updateRateCountPost(postId: String, rated: Int) {
//        disposable =
//            postRepository.updateRateCountPost(postId, rated)
//                .doOnSubscribe { showLoading() }
//                .doAfterTerminate { hideLoading() }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    { result -> MyLog.e("chung", result.toString()) },
//                    { error -> MyLog.e("this", error.message.toString()) },
//                    { Log.i("TAG", "Login Completed") }
//                )
//    }
}