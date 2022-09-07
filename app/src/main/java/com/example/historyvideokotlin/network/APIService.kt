package com.example.historyvideokotlin.network

import com.example.historyvideokotlin.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface APIService {

    @FormUrlEncoded
    @POST("getUser.php")
    fun getUser(@Field("userId") userId: String): Observable<List<User>>

    @GET("getVideo.php")
    fun getVideo(): Single<List<Video>>

    @GET("getQuiz.php")
    fun getQuiz(): Single<List<Quiz>>

    @GET("getTheme.php")
    fun getTheme(): Single<List<Theme>>

    @GET("getPost.php")
    fun getPost(): Single<List<Post>>

    @POST("postUser.php")
    fun postUser(
        @Body postUserRequest: PostUserRequest
    ): Completable

    @FormUrlEncoded
    @POST("postUser.php")
    fun postUser2(
        @Field("userId") userId: String,
        @Field("userName") userName: String,
        @Field("userEmail") userEmail: String
    ): Observable<List<User>>

    @FormUrlEncoded
    @POST("postComment.php")
    fun postComment(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int,
        @Field("content") content: String
    ): Observable<List<Comment>>

    @FormUrlEncoded
    @POST("postReply.php")
    fun postReply(
        @Field("userId") userId: String,
        @Field("partnerName") partnerName: String,
        @Field("commentId") commentId: String,
        @Field("content") content: String
    ): Observable<List<Reply>>

    @FormUrlEncoded
    @POST("getMyVideo.php")
    fun getMyVideoList(@Field("userId") userId: String): Observable<List<MyVideo>>

    @FormUrlEncoded
    @POST("getMyVideo.php")
    fun getMyVideo(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int
    ): Observable<List<MyVideo>>

    @FormUrlEncoded
    @POST("getMyPost.php")
    fun getMyPostList(@Field("userId") userId: String): Observable<List<MyPost>>

    @FormUrlEncoded
    @POST("getMyPost.php")
    fun getMyPost(@Field("userId") userId: String,
                  @Field("postId") postId: String): Observable<List<MyPost>>

    @FormUrlEncoded
    @POST("getComment.php")
    fun getComment(@Field("videoId") videoId: Int): Observable<List<Comment>>

    @FormUrlEncoded
    @POST("getReply.php")
    fun getReply(@Field("commentId") commentId: String): Observable<List<Reply>>

    @FormUrlEncoded
    @POST("getSearchPost.php")
    fun getSearchPost(@Field("keyword") keyword: String): Observable<List<Post>>

    @FormUrlEncoded
    @POST("getSearchVideo.php")
    fun getSearchVideo(@Field("keyword") keyword: String): Observable<List<Video>>

    @FormUrlEncoded
    @POST("updateVideoInfo.php")
    fun updateViewCountVideo(
        @Field("videoId") videoId: Int,
        @Field("viewed") viewed: Int
    ): Observable<Video>

    @FormUrlEncoded
    @POST("updateVideoInfo.php")
    fun updateLikeCountVideo(
        @Field("videoId") videoId: Int,
        @Field("liked") liked: Int
    ): Observable<Video>

    @FormUrlEncoded
    @POST("updateVideoInfo.php")
    fun updateDislikeCountVideo(
        @Field("videoId") videoId: Int,
        @Field("disliked") disliked: Int
    ): Observable<Video>

    @FormUrlEncoded
    @POST("updateVideoInfo.php")
    fun updateLikeCancelVideo(
        @Field("videoId") videoId: Int,
        @Field("likeCancel") likeCancel: Int
    ): Observable<Video>

    @FormUrlEncoded
    @POST("updateVideoInfo.php")
    fun updateDislikeCancelVideo(
        @Field("videoId") videoId: Int,
        @Field("dislikeCancel") dislikeCancel: Int
    ): Observable<Video>

    @FormUrlEncoded
    @POST("updateVideoInfo.php")
    fun updateCommentCountVideo(
        @Field("videoId") videoId: Int,
        @Field("commented") commented: Int
    ): Observable<Video>

    @FormUrlEncoded
    @POST("updateVideoInfo.php")
    fun updateShareCountVideo(
        @Field("videoId") videoId: Int,
        @Field("shared") shared: Int
    ): Observable<Video>

    @FormUrlEncoded
    @POST("updateCommentInfo.php")
    fun updateLikeCountComment(
        @Field("commentId") commentId: String,
        @Field("liked") liked: Int
    ): Observable<Comment>

    @FormUrlEncoded
    @POST("updateCommentInfo.php")
    fun updateDisikeCountComment(
        @Field("commentId") commentId: String,
        @Field("disliked") disliked: Int
    ): Observable<Comment>

    @FormUrlEncoded
    @POST("updateCommentInfo.php")
    fun updateReplyCountComment(
        @Field("commentId") commentId: String,
        @Field("replied") replied: Int
    ): Observable<Comment>

    @FormUrlEncoded
    @POST("updateReplyInfo.php")
    fun updateLikeCountReply(
        @Field("replyId") replyId: String,
        @Field("liked") liked: Int
    ): Observable<Reply>

    @FormUrlEncoded
    @POST("updateReplyInfo.php")
    fun updateDislikeCountReply(
        @Field("replyId") replyId: String,
        @Field("disliked") disliked: Int
    ): Observable<Reply>

    @FormUrlEncoded
    @POST("updatePostInfo.php")
    fun updateReadCountPost(
        @Field("postId") postId: String,
        @Field("read") read: Int
    ): Observable<Post>

    @FormUrlEncoded
    @POST("updatePostInfo.php")
    fun updateRateCountPost(
        @Field("postId") postId: String,
        @Field("rated") rated: Int
    ): Observable<Post>

    @FormUrlEncoded
    @POST("updatePostInfo.php")
    fun updateDownloadCountPost(
        @Field("postId") postId: String,
        @Field("downloaded") downloaded: Int
    ): Observable<Post>


    @FormUrlEncoded
    @POST("updateMyVideo.php")
    fun updateViewedMyVideo(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int,
        @Field("isViewed") isViewed: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("updateMyVideo.php")
    fun updateLikeMyVideo(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int,
        @Field("isLiked") isLiked: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("updateMyVideo.php")
    fun updateLaterMyVideo(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int,
        @Field("isLatered") isLatered: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("updateMyVideo.php")
    fun updateDownloadMyVideo(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int,
        @Field("isDownloaded") isDownloaded: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("updateMyVideo.php")
    fun updateShareMyVideo(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int,
        @Field("isShared") isShared: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("updateMyVideo.php")
    fun updateDontCareMyVideo(
        @Field("userId") userId: String,
        @Field("videoId") videoId: Int,
        @Field("isDontCared") isDontCared: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("deleteMyVideo.php")
    fun deleteViewMyVideo(
        @Field("myVideoId") myVideoId: Int,
        @Field("isViewed") isViewed: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("deleteMyVideo.php")
    fun deleteLikeMyVideo(
        @Field("myVideoId") myVideoId: Int,
        @Field("isLiked") isLiked: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("deleteMyVideo.php")
    fun deleteLaterMyVideo(
        @Field("myVideoId") myVideoId: Int,
        @Field("isLatered") isLatered: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("deleteMyVideo.php")
    fun deleteDownloadMyVideo(
        @Field("myVideoId") myVideoId: Int,
        @Field("isDownloaded") isDownloaded: Int
    ): Observable<MyVideo>

    @FormUrlEncoded
    @POST("deleteMyPost.php")
    fun deleteMyPost(
        @Field("myPostId") myPostId: Int,
        @Field("isDownloaded") isDownloaded: Int
    ): Observable<MyPost>
}