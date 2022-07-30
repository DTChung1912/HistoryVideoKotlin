package com.example.historyvideokotlin.Repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.PostUserRequest
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.model.Video
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class UserRepository : BaseRepository() {

    fun getUser(): Single<List<User>> {
        return apiService?.getUser()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())!!
    }

    fun getUser2(): Single<List<User>> {
        return apiService?.getUser()!!
    }

    fun postUser(name: String, email: String): Completable {
        val postUserRequest = PostUserRequest(name,email)
        return apiService?.postUser(postUserRequest)!!
    }

    fun postUser2(name: String, email: String): Observable<List<User>> {
        return apiService?.postUser2(name,email)!!
    }

    fun getViewedVideo(userId : String, videoId : String) : Single<List<Video>> {
        return apiService?.getViewedVideo(userId,videoId)!!
    }


}