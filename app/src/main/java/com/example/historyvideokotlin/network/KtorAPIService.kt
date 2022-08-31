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

    @GET("quiz/")
    suspend fun getQuiz(): Response<List<Quiz>>

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

    @POST("myvideo/list")
    suspend fun getMyVideoList(@Query("user_id") userId: String): Response<List<MyVideo>>

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

    @GET("mypost/")
    suspend fun getMyPostList(@Query("user_id") userId: String): Response<List<MyPost>>

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

    @PUT("comment/update")
    suspend fun updateComment(
        @Query("commentId") commentId: Int,
        @Body comment: Comment
    ): Response<Comment>

    @PUT("reply/update.php")
    suspend fun updateReply(
        @Query("reply_id") replyId: Int,
        @Body reply: Reply
    ): Response<Reply>


    @PUT("myvideo/update")
    suspend fun updateMyVideo(
        @Query("myvideo_id") myVideoId: String,
        @Body myVideo: MyVideo
    ): Response<MyVideo>

    @DELETE("myvideo/delete")
    suspend fun deleteLikeMyVideo(
        @Field("myvideo_id") myVideoId: Int
    ): Response<MyVideo>


    @DELETE("mypost/delete")
    suspend fun deleteMyPost(
        @Field("mypost_id") myPostId: String
    ): Response<MyPost>
}