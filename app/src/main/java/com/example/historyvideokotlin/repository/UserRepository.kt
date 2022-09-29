package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.*
import io.reactivex.Completable
import io.reactivex.Observable

class UserRepository : BaseRepository() {

//    fun getUser(): Observable<List<User>> {
//        return apiService?.getUser()?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())!!
//    }

    fun getUser(userId: String): Observable<List<User>> {
        return apiService?.getUser(userId)!!
    }

    fun postUser(name: String, email: String): Completable {
        val postUserRequest = PostUserRequest(name, email)
        return apiService?.postUser(postUserRequest)!!
    }

    fun postUser2(userId: String, name: String, email: String): Observable<List<User>> {
        return apiService?.postUser2(userId, name, email)!!
    }

    fun getMyVideoList(userId: String): Observable<List<MyVideo>> {
        return apiService?.getMyVideoList(userId)!!
    }

    fun getMyPostList(userId: String): Observable<List<MyPost>> {
        return apiService?.getMyPostList(userId)!!
    }

    fun getMyVideo(userId: String, videoId: Int): Observable<List<MyVideo>> {
        return apiService?.getMyVideo(userId, videoId)!!
    }

    fun updateViewedMyVideo(userId: String, videoId: Int, isView: Int): Observable<MyVideo> {
        return apiService?.updateViewedMyVideo(userId, videoId, isView)!!
    }

    fun updateLikeMyVideo(userId: String, videoId: Int, isLike: Int): Observable<MyVideo> {
        return apiService?.updateLikeMyVideo(userId, videoId, isLike)!!
    }

    fun updateLaterMyVideo(userId: String, videoId: Int, isLike: Int): Observable<MyVideo> {
        return apiService?.updateLaterMyVideo(userId, videoId, isLike)!!
    }

    fun updateDownloadMyVideo(userId: String, videoId: Int, isLike: Int): Observable<MyVideo> {
        return apiService?.updateDownloadMyVideo(userId, videoId, isLike)!!
    }

    fun updateShareMyVideo(userId: String, videoId: Int, isLike: Int): Observable<MyVideo> {
        return apiService?.updateShareMyVideo(userId, videoId, isLike)!!
    }

    fun updateDontCareMyVideo(
        userId: String,
        videoId: Int,
        isDontCared: Int
    ): Observable<MyVideo> {
        return apiService?.updateDontCareMyVideo(userId, videoId, isDontCared)!!
    }

    fun deleteViewMyVideo(myVideoId: Int, isViewed: Int): Observable<MyVideo> {
        return apiService?.deleteViewMyVideo(myVideoId, isViewed)!!
    }

    fun deleteLikeMyVideo(myVideoId: Int, isLiked: Int): Observable<MyVideo> {
        return apiService?.deleteLikeMyVideo(myVideoId, isLiked)!!
    }

    fun deleteDownloadMyVideo(myVideoId: Int, isDownloaded: Int): Observable<MyVideo> {
        return apiService?.deleteDownloadMyVideo(myVideoId, isDownloaded)!!
    }

    fun deleteLaterMyVideo(myVideoId: Int, isLatered: Int): Observable<MyVideo> {
        return apiService?.deleteLaterMyVideo(myVideoId, isLatered)!!
    }
}
