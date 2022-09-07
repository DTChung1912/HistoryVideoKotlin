package com.example.historyvideokotlin.repository

import com.example.historyvideokotlin.base.BaseRepository
import com.example.historyvideokotlin.model.Comment
import com.example.historyvideokotlin.model.Reply
import com.example.historyvideokotlin.model.Video
import io.reactivex.Observable
import io.reactivex.Single

class VideoRepository : BaseRepository() {

    fun getVideo(): Single<List<Video>> {
        return apiService?.getVideo()!!
    }

    fun getComment(videoId: Int): Observable<List<Comment>> {
        return apiService?.getComment(videoId)!!
    }

    fun getReply(commentId: String): Observable<List<Reply>> {
        return apiService?.getReply(commentId)!!
    }

    fun postComment(userId: String, videoId: Int, content: String): Observable<List<Comment>> {
        return apiService?.postComment(userId, videoId, content)!!
    }

    fun postReply(
        userId: String,
        partnerName: String,
        commentId: String,
        content: String
    ): Observable<List<Reply>> {
        return apiService?.postReply(userId, partnerName, commentId, content)!!
    }

    fun updateViewCountVideo(videoId: Int, viewed: Int): Observable<Video> {
        return apiService?.updateViewCountVideo(videoId, viewed)!!
    }

    fun updateLikeCountVideo(videoId: Int, liked: Int): Observable<Video> {
        return apiService?.updateLikeCountVideo(videoId, liked)!!
    }

    fun updateDislikeCountVideo(videoId: Int, disliked: Int): Observable<Video> {
        return apiService?.updateDislikeCountVideo(videoId, disliked)!!
    }

    fun updateLikeCancelVideo(videoId: Int, likeCancel: Int): Observable<Video> {
        return apiService?.updateLikeCancelVideo(videoId, likeCancel )!!
    }

    fun updateDislikeCancelVideo(videoId: Int, dislikeCancel: Int): Observable<Video> {
        return apiService?.updateDislikeCancelVideo(videoId, dislikeCancel )!!
    }

    fun updateCommentCountVideo(videoId: Int, commented: Int): Observable<Video> {
        return apiService?.updateCommentCountVideo(videoId, commented)!!
    }

    fun updateShareCountVideo(videoId: Int, shared: Int): Observable<Video> {
        return apiService?.updateShareCountVideo(videoId, shared)!!
    }

    fun updateLikeCountComment(commentId: String, liked: Int): Observable<Comment> {
        return apiService?.updateLikeCountComment(commentId, liked)!!
    }

    fun updateDisikeCountComment(commentId: String, disliked: Int): Observable<Comment> {
        return apiService?.updateDisikeCountComment(commentId, disliked)!!
    }

    fun updateReplyCountComment(commentId: String, replied: Int): Observable<Comment> {
        return apiService?.updateReplyCountComment(commentId, replied)!!
    }

    fun updateLikeCountReply(replyId: String, liked: Int): Observable<Reply> {
        return apiService?.updateLikeCountReply(replyId, liked)!!
    }

    fun updateDislikeCountReply(replyId: String, disliked: Int): Observable<Reply> {
        return apiService?.updateDislikeCountReply(replyId, disliked)!!
    }
}