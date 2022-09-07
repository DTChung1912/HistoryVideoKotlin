package com.example.historyvideokotlin.network

import com.example.historyvideokotlin.model.*
import io.reactivex.Completable
import retrofit2.Response
import retrofit2.http.*

interface KtorAPIService {

    @GET("user/")
    suspend fun getUser(@Path("userId") userId: String): Response<List<User>>

    @GET("video/")
    suspend fun getVideo(): Response<List<Video>>

    @GET("quiz/random")
    suspend fun getRandomQuiz(): Response<List<Quiz>>

    @GET("quiz/theme")
    suspend fun getQuizByTheme(@Query("theme_id") themeInt: Int): Response<List<Quiz>>

    @GET("theme/")
    suspend fun getTheme(): Response<List<Theme>>

    @GET("post/")
    suspend fun getPost(): Response<List<Post>>

    @GET("comment/{video_id}")
    suspend fun getComment(@Path("video_id") videoId: Int): Response<List<Comment>>

    @GET("reply/")
    suspend fun getReply(@Field("comment_id") commentId: String): Response<List<Reply>>

    @POST("user/register")
    suspend fun register(
        @Body user: User
    ): Response<List<User>>

    @POST("comment/create")
    suspend fun postComment(
        @Body comment: Comment
    ): Response<List<Comment>>

    @POST("reply/create")
    suspend fun postReply(
        @Body reply: Reply
    ): Response<List<Reply>>

    @GET("myvideo/list")
    suspend fun getMyVideoList(
        @Query("user_id") userId: String
    ): Response<List<MyVideo>>

    @GET("myvideo/detail")
    suspend fun getMyVideo(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int
    ): Response<List<MyVideo>>

    @GET("video/test")
    suspend fun getTest(
        @Query("test1") test1: String?,
        @Query("test2") test2: String?
    ): Response<String>

    @GET("mypost/list")
    suspend fun getMyPostList(
        @Query("user_id") userId: String
    ): Response<List<MyPost>>

    @GET("mypost/detail")
    suspend fun getMyPost(
        @Query("user_id") userId: String,
        @Query("post_id") postId: Int
    ): Response<List<MyPost>>

    @POST("mypost/create")
    suspend fun postMyPost(
        @Body myPost: MyPost
    ): Response<List<MyPost>>

    @GET("post/search")
    suspend fun getSearchPost(@Query("keyword") keyword: String): Response<List<Post>>

    @GET("video/search")
    suspend fun getSearchVideo(@Query("keyword") keyword: String): Response<List<Video>>

    @PUT("user/update")
    suspend fun updateUser(
        @Query("user_id") userId: String,
        @Body user: User
    ): Response<User>

    @PUT("post/update")
    suspend fun updatePost(
        @Query("post_id") postId: Int,
        @Body post: Post
    ): Response<Post>

    @PUT("video/update")
    fun updateVideo(
        @Query("video_id") videoId: Int,
        @Body video: Video
    ): Response<Video>

    // Update Post

    @PUT("post/update/read")
    fun updatePostRead(
        @Query("post_id") postId: Int
    ): Response<Post>

    @PUT("post/update/download")
    fun updatePostDownload(
        @Query("post_id") postId: Int
    ): Response<Post>

    @PUT("post/update/rate")
    fun updatePostRate(
        @Query("post_id") postId: Int
    ): Response<Post>

    // Update Video

    @PUT("video/update/view")
    fun updateVideoView(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/like")
    fun updateVideoLike(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/dislike")
    fun updateVideoDislike(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/like/cancel")
    fun updateVideoLikeCancel(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/dislike/cancel")
    fun updateVideoDislikeCancel(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/later")
    fun updateVideoLater(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/download")
    fun updateVideoDownload(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/share")
    fun updateVideoShare(
        @Query("video_id") videoId: Int
    ): Response<Video>

    @PUT("video/update/comment")
    fun updateVideoComment(
        @Query("video_id") videoId: Int
    ): Response<Video>

    // Update Comment

    @PUT("comment/update/like")
    fun updateCommentLike(
        @Query("comment_id") commentId: Int
    ): Response<Comment>

    @PUT("comment/update/dislike")
    fun updateCommentDislike(
        @Query("comment_id") commentId: Int
    ): Response<Comment>

    @PUT("comment/update/like/cancel")
    fun updateCommentLikeCancel(
        @Query("comment_id") commentId: Int
    ): Response<Comment>

    @PUT("comment/update/dislike/cancel")
    fun updateCommentDislikeCancel(
        @Query("comment_id") commentId: Int
    ): Response<Comment>

    @PUT("comment/update/reply")
    fun updateCommentReply(
        @Query("video_id") commentId: Int
    ): Response<Comment>

    // Update Reply

    @PUT("reply/update/like")
    fun updateReplyLike(
        @Query("reply_id") replyId: Int
    ): Response<Reply>

    @PUT("reply/update/dislike")
    fun updateReplyDislike(
        @Query("reply_id") replyId: Int
    ): Response<Reply>

    @PUT("reply/update/like/cancel")
    fun updateReplyLikeCancel(
        @Query("reply_id") replyId: Int
    ): Response<Reply>

    @PUT("reply/update/dislike/cancel")
    fun updateReplyDislikeCancel(
        @Query("reply_id") replyId: Int
    ): Response<Reply>

//    @PUT("myvideo/update")
//    suspend fun updateMyVideo(
//        @Query("myvideo_id") myVideoId: String,
//        @Body myVideo: MyVideo
//    ): Response<MyVideo>

    // Update MyPost

    @PUT("mypost/update/read")
    fun updateMyPostRead(
        @Query("my_post_id") myPostId: Int,
        @Query("is_read") isRead: Int
    ): Response<MyPost>

    @PUT("mypost/update/download")
    fun updateMyPostDownload(
        @Query("my_post_id") myPostId: Int,
        @Query("is_download") isDownload: Int
    ): Response<MyPost>

    @PUT("mypost/update/read")
    fun updateMyPostRate(
        @Query("my_post_id") myPostId: Int,
        @Query("rate") rate: Int
    ): Response<MyPost>

    // Update MY Video

    @PUT("myvideo/update/view")
    fun updateMyVideoView(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_view") isView: Int
    ): Response<MyVideo>

    @PUT("myvideo/update/download")
    fun updateMyVideoDownload(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_download") isDownload: Int
    ): Response<MyVideo>

    @PUT("myvideo/update/like")
    fun updateMyVideoLike(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_like") isLike: Int
    ): Response<MyVideo>

    @PUT("myvideo/update/later")
    fun updateMyVideoLater(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_later") isLater: Int
    ): Response<MyVideo>

    @PUT("myvideo/update/dontcare")
    fun updateMyVideoDontCare(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_dont_care") isDontCare: Int
    ): Response<MyVideo>

    @PUT("myvideo/update/viewtime")
    fun updateMyVideoViewTime(
        @Query("my_video_id") myVideoId: Int,
        @Query("view_time") viewTime: Int
    ): Response<MyVideo>

    // Delete

    @DELETE("comment/delete")
    suspend fun deleteComment(
        @Query("comment_id") commentId: Int
    ): Response<Comment>

    @DELETE("reply/delete")
    suspend fun deleteReply(
        @Query("reply_id") replyId: Int
    ): Response<Reply>

    @DELETE("myvideo/delete")
    suspend fun deleteMyVideo(
        @Query("my_video_id") myVideoId: Int
    ): Response<MyVideo>

    @DELETE("mypost/delete")
    suspend fun deleteMyPost(
        @Query("my_post_id") myPostId: Int
    ): Response<MyPost>
}