package com.example.historyvideokotlin.network

import com.example.historyvideokotlin.model.*
import retrofit2.Response
import retrofit2.http.*

interface KtorAPIService {

    @GET("user/detail")
    suspend fun getUser(@Query("userId") userId: String): Response<User>

    @GET("video/list")
    suspend fun getVideo(): Response<List<Video>>

    @GET("quiz/random")
    suspend fun getRandomQuiz(): Response<List<Quiz>>

    @GET("quiz/theme")
    suspend fun getQuizByTheme(@Query("theme_id") themeInt: Int): Response<List<Quiz>>

    @GET("theme/list")
    suspend fun getTheme(): Response<List<Theme>>

    @GET("post/list")
    suspend fun getPost(): Response<List<Post>>

    @GET("comment/list")
    suspend fun getComment(@Query("video_id") videoId: Int): Response<List<Comment>>

    @GET("reply/list")
    suspend fun getReply(@Query("comment_id") commentId: String): Response<List<Reply>>

    @GET("post/search")
    suspend fun getSearchPost(@Query("keyword") keyword: String): Response<List<Post>>

    @GET("video/search")
    suspend fun getSearchVideo(@Query("keyword") keyword: String): Response<List<Video>>

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

    @PUT("user/update")
    suspend fun updateUser(
        @Body user: User
    ): Response<User>

    @PUT("post/update")
    suspend fun updatePost(
        @Query("post_id") postId: Int,
        @Body post: Post
    ): Response<Post>

    @PUT("video/update")
    suspend fun updateVideo(
        @Query("video_id") videoId: Int,
        @Body video: Video
    ): Response<Video>

    // Update Post
    @PUT("post/update/read")
    suspend fun updatePostRead(
        @Query("post_id") postId: Int
    ): Response<UpdateResponse>

    @PUT("post/update/download")
    suspend fun updatePostDownload(
        @Query("post_id") postId: Int
    ): Response<UpdateResponse>

    @PUT("post/update/rate")
    suspend fun updatePostRate(
        @Query("post_id") postId: Int
    ): Response<UpdateResponse>

    // Update Video

    @PUT("video/update/view")
    suspend fun updateVideoView(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    @PUT("video/update/like")
    suspend fun updateVideoLike(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    @PUT("video/update/dislike")
    suspend fun updateVideoDislike(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    @PUT("video/update/like/cancel")
    suspend fun updateVideoLikeCancel(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    @PUT("video/update/dislike/cancel")
    suspend fun updateVideoDislikeCancel(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    @PUT("video/update/download")
    suspend fun updateVideoDownload(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    @PUT("video/update/comment")
    suspend fun updateVideoComment(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    // Update Comment

    @PUT("comment/update/like")
    suspend fun updateCommentLike(
        @Query("comment_id") commentId: Int
    ): Response<UpdateResponse>

    @PUT("comment/update/dislike")
    suspend fun updateCommentDislike(
        @Query("comment_id") commentId: Int
    ): Response<UpdateResponse>

    @PUT("comment/update/like/cancel")
    suspend fun updateCommentLikeCancel(
        @Query("comment_id") commentId: Int
    ): Response<UpdateResponse>

    @PUT("comment/update/dislike/cancel")
    suspend fun updateCommentDislikeCancel(
        @Query("comment_id") commentId: Int
    ): Response<UpdateResponse>

    @PUT("comment/update/reply")
    suspend fun updateCommentReply(
        @Query("video_id") commentId: Int
    ): Response<UpdateResponse>

    // Update Reply

    @PUT("reply/update/like")
    suspend fun updateReplyLike(
        @Query("reply_id") replyId: Int
    ): Response<UpdateResponse>

    @PUT("reply/update/dislike")
    suspend fun updateReplyDislike(
        @Query("reply_id") replyId: Int
    ): Response<UpdateResponse>

    @PUT("reply/update/like/cancel")
    suspend fun updateReplyLikeCancel(
        @Query("reply_id") replyId: Int
    ): Response<UpdateResponse>

    @PUT("reply/update/dislike/cancel")
    suspend fun updateReplyDislikeCancel(
        @Query("reply_id") replyId: Int
    ): Response<UpdateResponse>

//    @PUT("myvideo/update")
//    suspend fun updateMyVideo(
//        @Query("myvideo_id") myVideoId: String,
//        @Body myVideo: MyVideo
//    ): Response<MyVideo>

    // Update MyPost

    @PUT("mypost/update/read")
    suspend fun updateMyPostRead(
        @Query("my_post_id") myPostId: Int,
        @Query("is_read") isRead: Int
    ): Response<UpdateResponse>

    @PUT("mypost/update/download")
    suspend fun updateMyPostDownload(
        @Query("my_post_id") myPostId: Int,
        @Query("is_download") isDownload: Int
    ): Response<UpdateResponse>

    @PUT("mypost/update/read")
    suspend fun updateMyPostRate(
        @Query("my_post_id") myPostId: Int,
        @Query("rate") rate: Int
    ): Response<UpdateResponse>

    // Update MY Video

    @PUT("myvideo/update/view")
    suspend fun updateMyVideoView(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_view") isView: Int
    ): Response<UpdateResponse>

    @PUT("myvideo/update/download")
    suspend fun updateMyVideoDownload(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_download") isDownload: Int
    ): Response<UpdateResponse>

    @PUT("myvideo/update/like")
    suspend fun updateMyVideoLike(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_like") isLike: Int
    ): Response<UpdateResponse>

    @PUT("myvideo/update/later")
    suspend fun updateMyVideoLater(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_later") isLater: Int
    ): Response<UpdateResponse>

    @PUT("myvideo/update/dontcare")
    suspend fun updateMyVideoDontCare(
        @Query("my_video_id") myVideoId: Int,
        @Query("is_dont_care") isDontCare: Int
    ): Response<UpdateResponse>

    @PUT("myvideo/update/viewtime")
    suspend fun updateMyVideoViewTime(
        @Query("my_video_id") myVideoId: Int,
        @Query("view_time") viewTime: Int
    ): Response<UpdateResponse>

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