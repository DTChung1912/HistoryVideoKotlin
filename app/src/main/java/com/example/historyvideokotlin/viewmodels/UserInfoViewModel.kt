package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.model.MyPost
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserInfoViewModel(application: Application) : BaseViewModel(application) {

    var disposable = CompositeDisposable()
    var myVideoList = MutableLiveData<List<MyVideo>>()
    var myPostList = MutableLiveData<List<MyPost>>()
    var userRepository = UserRepository()

    var disposable2: Disposable? = null
    var userList = MutableLiveData<List<User>>()

    var userId = HistoryUserManager.FUid()

    fun getMyVideoData() {
        getMyVideoList( )
        getMyPostList()
        getUser()
    }

    private fun getMyVideoList() {
        disposable.add(
            userRepository.getMyVideoList(userId)
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

    private fun getMyPostList() {
        disposable.add(
            userRepository.getMyPostList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribe(
                    { data ->
                        data.let {
                            myPostList.value = it
                        }
                    },
                    { error -> MyLog.e("this", error.message.toString()) }
                )
        )
    }

    fun getUser() {
        disposable2 =
            userRepository.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingLiveData.postValue(true) }
                .doAfterTerminate { loadingLiveData.postValue(false) }
                .subscribe(
                    { data ->
                        data.let {
                            userList.value = it
                        }
                    },
                    { error -> MyLog.e("this", error.message.toString()) }
                )
    }


}