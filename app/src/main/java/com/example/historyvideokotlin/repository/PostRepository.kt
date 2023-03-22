package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.*
import io.reactivex.Observable
import io.reactivex.Single

class PostRepository : BaseRepository() {

    fun getPost(): Single<List<Post>> {
        return apiService?.getPost()!!
    }

    fun updateReadCountPost(postId: String, read: Int): Observable<Post> {
        return apiService?.updateReadCountPost(postId, read)!!
    }

    fun updateDownloadCountPost(postId: String, downloaded: Int): Observable<Post> {
        return apiService?.updateDownloadCountPost(postId, downloaded)!!
    }

    fun updateRateCountPost(postId: String, rated: Int): Observable<Post> {
        return apiService?.updateRateCountPost(postId, rated)!!
    }
    //comiit
    //comiit 2
}