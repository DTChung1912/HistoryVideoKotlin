package com.example.historyvideokotlin.Repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.Reply
import com.example.historyvideokotlin.model.Video
import io.reactivex.Observable
import io.reactivex.Single

class VideoRepository : BaseRepository() {

    fun getVideo() : Single<List<Video>> {
        return apiService?.getVideo()!!
    }

    fun getComment(videoId : String) : Observable<List<Comment>> {
        return apiService?.getComment(videoId)!!
    }

    fun getReply(commentId : String) : Observable<List<Reply>> {
        return apiService?.getReply(commentId)!!
    }

    fun updateViewedVideo(userId : String, videoId: String) : Observable<Video>{
        return apiService?.updateViewedVideo(userId, videoId)!!
    }

    fun updateStatusVideo(userId : String, videoId: String, videoStatus : Int) : Observable<Video>{
        return apiService?.updateStatusVideo(userId, videoId, videoStatus)!!
    }
}