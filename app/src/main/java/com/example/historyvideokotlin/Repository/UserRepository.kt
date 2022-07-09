package com.example.historyvideokotlin.Repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserRepository : BaseRepository() {

    fun getUser(): Single<List<User>> {
        return apiService?.getUser()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())!!
    }

    fun getUser2() : Single<List<User>> {
        return apiService?.getUser()!!
    }

}