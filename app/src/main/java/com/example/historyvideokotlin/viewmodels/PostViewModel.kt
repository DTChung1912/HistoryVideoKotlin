package com.example.historyvideokotlin.viewmodels

import android.app.Application
import android.content.Context
import android.os.UserManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.Repository.PostRepository
import com.example.historyvideokotlin.Repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.*
import com.facebook.login.LoginFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PostViewModel(application: Application) : BaseViewModel(application) {

    var disposable = CompositeDisposable()
    var postPerson = MutableLiveData<List<PostPerson>>()
    var postPlace = MutableLiveData<List<PostPlace>>()
    var postEvent = MutableLiveData<List<PostEvent>>()
    var postTimeline = MutableLiveData<List<PostTimeline>>()
    var postRepository = PostRepository()

    fun getPostData() {
        getPostPerson()
        getPostEvent()
        getPostPlace()
        getPostTimeline()
    }

    private fun getPostPerson() {
        disposable.add(
            postRepository.getPostPerson()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PostPerson>>() {
                    override fun onSuccess(t: List<PostPerson>) {
                        postPerson.value = t
                        Log.e("chung" , "Ok")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("chung" , e.message.toString())
                    }

                })
        )
    }

    private fun getPostPlace() {
        disposable.add(
            postRepository.getPostPlace()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PostPlace>>() {
                    override fun onSuccess(t: List<PostPlace>) {
                        postPlace.value = t
                        Log.e("chung" , "Ok")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("chung" , e.message.toString())
                    }

                })
        )
    }

    private fun getPostEvent() {
        disposable.add(
            postRepository.getPostEvent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PostEvent>>() {
                    override fun onSuccess(t: List<PostEvent>) {
                        postEvent.value = t
                        Log.e("chung" , "Ok")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("chung" , e.message.toString())
                    }

                })
        )
    }

    private fun getPostTimeline() {
        disposable.add(
            postRepository.getPostTimeline()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<PostTimeline>>() {
                    override fun onSuccess(t: List<PostTimeline>) {
                        postTimeline.value = t
                        Log.e("chung" , "Ok")
                    }

                    override fun onError(e: Throwable) {
                        Log.e("chung" , e.message.toString())
                    }

                })
        )
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