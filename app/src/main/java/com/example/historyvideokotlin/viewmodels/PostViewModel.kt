package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.repository.PostRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : BaseViewModel(application) {

    var disposable = CompositeDisposable()
    var postList = MutableLiveData<List<Post>>()
    var postRepository = PostRepository()

    val ktorPostRepository = application.repositoryProvider.ktorPostRepository

    private val _isLoadFail = MutableLiveData(false)
    val isLoadFail: LiveData<Boolean> get() = _isLoadFail

    fun getPostData() {
        getPostKtor()
    }

    private fun getPost() {
        disposable.add(
            postRepository.getPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribeWith(object : DisposableSingleObserver<List<Post>>() {
                    override fun onSuccess(t: List<Post>) {
                        postList.value = t
                        MyLog.e("chung", "Ok")
                    }

                    override fun onError(e: Throwable) {
                        MyLog.e("chung", e.message.toString())
                    }
                })
        )
    }

    private fun getPostKtor() {
        _isLoadFail.value = false
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorPostRepository.getPostList()
            }.onSuccess {
                hideLoading()
                postList.value = it
                _isLoadFail.value = false
            }.onFailure {
                hideLoading()
                MyLog.e("postList",it.message)
                _isLoadFail.value = true
            }
        }
    }

    fun updatePostDownload(postId: Int) {
        viewModelScope.launch {
            runCatching {
                ktorPostRepository.updatePostDownload(postId)
            }.onSuccess {
                MyLog.e("updatePostDownload: ",it.isSuccess.toString())
            }.onFailure {
                MyLog.e("updatePostDownload: ",it.message.toString())
            }
        }
    }

    fun getTabTitleIds(): List<Int> {
        val titleIds = mutableListOf<Int>()
        titleIds.run {
            add(R.string.title_post_tab_person)
            add(R.string.title_post_tab_events)
            add(R.string.title_post_tab_places)
            add(R.string.title_post_tab_timeline)
        }
        return titleIds
    }
}