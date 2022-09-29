package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.repository.UserRepository
import com.example.historyvideokotlin.base.BaseViewModel
import com.example.historyvideokotlin.di.repositoryProvider
import com.example.historyvideokotlin.model.MyPost
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.MyLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class UserInfoViewModel(application: Application) : BaseViewModel(application) {

    var disposable = CompositeDisposable()
    var myVideoList = MutableLiveData<List<MyVideo>>()
    var myPostList = MutableLiveData<List<MyPost>>()
    var userRepository = UserRepository()

    var disposable2: Disposable? = null
    var userList = MutableLiveData<List<User>>()
    var userInfo = MutableLiveData<User>()

    var userId = HistoryUserManager.instance.UserId()
    val ktorUserRepository = application.repositoryProvider.ktorUserRepository

    fun getMyVideoData() {
        getMyVideoList( )
        getMyPostList()
        getUser()
        getUserInfo(userId)
    }

    private fun getMyVideoList() {
        disposable.add(
            userRepository.getMyVideoList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
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
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
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
                .doOnSubscribe { showLoading() }
                .doAfterTerminate { hideLoading() }
                .subscribe(
                    { data ->
                        data.let {
                            userList.value = it
                        }
                    },
                    { error -> MyLog.e("this", error.message.toString()) }
                )
    }

    fun getUserInfo(userId: String) {
        viewModelScope.launch {
            runCatching {
                showLoading()
                ktorUserRepository.getUser(userId)
            }.onSuccess {
                hideLoading()
                userInfo.value = it
            }.onFailure {
                hideLoading()
                MyLog.e("postList",it.message)
            }
        }
    }


}