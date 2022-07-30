package com.example.historyvideokotlin.Repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.*
import io.reactivex.Single

class PostRepository : BaseRepository() {
    fun getPostPerson() : Single<List<PostPerson>> {
        return apiService?.getPostPerson()!!
    }

    fun getPostEvent() : Single<List<PostEvent>> {
        return apiService?.getPostEvent()!!
    }

    fun getPostPlace() : Single<List<PostPlace>> {
        return apiService?.getPostPlace()!!
    }

    fun getPostTimeline() : Single<List<PostTimeline>> {
        return apiService?.getPostTimeline()!!
    }

}