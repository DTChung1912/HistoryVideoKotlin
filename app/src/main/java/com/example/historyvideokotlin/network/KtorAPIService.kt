package com.example.historyvideokotlin.network

import com.example.historyvideokotlin.model.*
import retrofit2.Response
import retrofit2.http.*

interface KtorAPIService {

    @GET("user/list")
    suspend fun getUserList(): Response<List<User>>

    @GET("user/detail")
    suspend fun getUser(@Query("user_id") userId: String): Response<User>

    @GET("video/list")
    suspend fun getVideoList(): Response<List<Video>>

    @GET("video/detail")
    suspend fun getVideoDetail(
        @Query("video_id") videoId: Int
    ): Response<List<Video>>

    @GET("quiz/random")
    suspend fun getRandomQuiz(): Response<List<Quiz>>

    @GET("quiz/theme")
    suspend fun getQuizByTheme(@Query("theme_id") themeInt: Int): Response<List<Quiz>>

    @GET("theme/list")
    suspend fun getTheme(): Response<List<Theme>>

    @GET("post/list")
    suspend fun getPostList(): Response<List<Post>>

    @GET("post/detail")
    suspend fun getPostDetail(
        @Query("post_id") postId: Int
    ): Response<List<Post>>

    @GET("comment/list")
    suspend fun getComment(@Query("video_id") videoId: Int): Response<List<Comment>>

    @GET("reply/list")
    suspend fun getReply(@Query("comment_id") commentId: Int): Response<List<Reply>>

    @GET("post/search")
    suspend fun getSearchPost(@Query("keyword") keyword: String): Response<List<Post>>

    @GET("video/search")
    suspend fun getSearchVideo(@Query("keyword") keyword: String): Response<List<Video>>

    @GET("video/next")
    suspend fun getNextVideo(@Query("video_id") video_id: Int): Response<List<Video>>

    @POST("user/register")
    suspend fun register(
        @Body user: User
    ): Response<List<User>>

    @POST("post/create")
    suspend fun createPost(
        @Body post: PostRequest
    ): Response<CreateResponse>

    @POST("video/create")
    suspend fun createVideo(
        @Body video: Video
    ): Response<CreateResponse>

    @POST("comment/create")
    suspend fun createComment(
        @Body comment: CommentRequest
    ): Response<CreateResponse>

    @POST("reply/create")
    suspend fun createReply(
        @Body reply: ReplyRequest
    ): Response<CreateResponse>

    @POST("theme/create")
    suspend fun createTheme(
        @Body theme: Theme
    ): Response<CreateResponse>

    @POST("quiz/create")
    suspend fun createQuiz(
        @Body quiz: Quiz
    ): Response<CreateResponse>

    @GET("myvideo/list")
    suspend fun getMyVideoList(
        @Query("user_id") userId: String
    ): Response<List<MyVideoModel>>

    @GET("myvideo/list")
    suspend fun getMyVideoListType(
        @Query("user_id") userId: String,
        @Query("type") type: Int
    ): Response<List<MyVideoModel>>

    @GET("myvideo/detail")
    suspend fun getMyVideo(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int
    ): Response<MyVideoStatus>

    @GET("mycomment/list")
    suspend fun getMyCommentList(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int
    ): Response<List<MyComment>>

    @GET("mycomment/detail")
    suspend fun getMyComment(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int,
        @Query("comment_id") commentId: Int
    ): Response<MyComment>

    @GET("myreply/list")
    suspend fun getMyReplyList(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int,
        @Query("comment_id") commentId: Int
    ): Response<List<MyReply>>

    @GET("myreply/detail")
    suspend fun getMyReply(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int,
        @Query("comment_id") commentId: Int,
        @Query("reply_id") replyId: Int
    ): Response<MyReply>

    @GET("video/test")
    suspend fun getTest(
        @Query("test1") test1: String?,
        @Query("test2") test2: String?
    ): Response<String>

    @GET("mypost/list")
    suspend fun getMyPostList(
        @Query("user_id") userId: String,
        @Query("type") type: Int
    ): Response<MyPostResponse>

    @GET("mypost/detail")
    suspend fun getMyPost(
        @Query("user_id") userId: String,
        @Query("post_id") postId: Int
    ): Response<List<MyPost>>

    @POST("mypost/create")
    suspend fun postMyPost(
        @Body myPost: MyPost
    ): Response<CreateResponse>

    @POST("myvideo/create")
    suspend fun postMyVideo(
        @Body myVideo: MyVideoStatus
    ): Response<CreateResponse>

    @PUT("user/update")
    suspend fun updateUser(
        @Body user: User
    ): Response<User>

    @PUT("post/update")
    suspend fun updatePost(
        @Body post: PostRequest
    ): Response<UpdateResponse>

    @PUT("video/update")
    suspend fun updateVideo(
        @Body video: Video
    ): Response<UpdateResponse>

    @PUT("quiz/update")
    suspend fun updateQuiz(
        @Body quiz: Quiz
    ): Response<UpdateResponse>

    @PUT("theme/update")
    suspend fun updateTheme(
        @Body theme: Theme
    ): Response<UpdateResponse>

    @DELETE
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
    ): Response<CountResponse>

    @PUT("video/update/dislike")
    suspend fun updateVideoDislike(
        @Query("video_id") videoId: Int
    ): Response<CountResponse>

    @PUT("video/update/like/cancel")
    suspend fun updateVideoLikeCancel(
        @Query("video_id") videoId: Int
    ): Response<CountResponse>

    @PUT("video/update/dislike/cancel")
    suspend fun updateVideoDislikeCancel(
        @Query("video_id") videoId: Int
    ): Response<CountResponse>

    @PUT("video/update/download")
    suspend fun updateVideoDownload(
        @Query("video_id") videoId: Int
    ): Response<UpdateResponse>

    @PUT("video/update/comment")
    suspend fun updateVideoComment(
        @Query("video_id") videoId: Int
    ): Response<CountResponse>

    @PUT("video/update/comment/cancel")
    suspend fun updateVideoCommentCancel(
        @Query("video_id") videoId: Int
    ): Response<CountResponse>

    // Update Comment

    @PUT("comment/update/like")
    suspend fun updateCommentLike(
        @Query("comment_id") commentId: Int
    ): Response<CountResponse>

    @PUT("comment/update/dislike")
    suspend fun updateCommentDislike(
        @Query("comment_id") commentId: Int
    ): Response<CountResponse>

    @PUT("comment/update/like/cancel")
    suspend fun updateCommentLikeCancel(
        @Query("comment_id") commentId: Int
    ): Response<CountResponse>

    @PUT("comment/update/dislike/cancel")
    suspend fun updateCommentDislikeCancel(
        @Query("comment_id") commentId: Int
    ): Response<CountResponse>

    @PUT("comment/update/reply")
    suspend fun updateCommentReply(
        @Query("video_id") commentId: Int
    ): Response<CountResponse>

    @PUT("comment/update/reply/cancel")
    suspend fun updateCommentReplyCancel(
        @Query("video_id") commentId: Int
    ): Response<CountResponse>

    // Update Reply

    @PUT("reply/update/like")
    suspend fun updateReplyLike(
        @Query("reply_id") replyId: Int
    ): Response<CountResponse>

    @PUT("reply/update/dislike")
    suspend fun updateReplyDislike(
        @Query("reply_id") replyId: Int
    ): Response<CountResponse>

    @PUT("reply/update/like/cancel")
    suspend fun updateReplyLikeCancel(
        @Query("reply_id") replyId: Int
    ): Response<CountResponse>

    @PUT("reply/update/dislike/cancel")
    suspend fun updateReplyDislikeCancel(
        @Query("reply_id") replyId: Int
    ): Response<CountResponse>

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

    // Update My Comment

    @PUT("mycomment/update/like")
    suspend fun updateMyCommentLike(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int,
        @Query("comment_id") commentId: Int,
        @Query("is_like") isLike: Int
    ): Response<UpdateResponse>

    // Update My Reply

    @PUT("myreply/update/like")
    suspend fun updateMyReplyLike(
        @Query("user_id") userId: String,
        @Query("video_id") videoId: Int,
        @Query("comment_id") commentId: Int,
        @Query("reply_id") replyId: Int,
        @Query("is_like") isLike: Int
    ): Response<UpdateResponse>

    // Delete

    @DELETE("user/delete")
    suspend fun deleteUser(
        @Query("user_id") userId: String
    ): Response<DeleteResponse>

    @DELETE("video/delete")
    suspend fun deleteVideo(
        @Query("video_id") videoId: Int
    ): Response<DeleteResponse>

    @DELETE("post/delete")
    suspend fun deletePost(
        @Query("post_is") postId: Int
    ): Response<DeleteResponse>

    @DELETE("theme/delete")
    suspend fun deleteTheme(
        @Query("theme_id") themeInt: Int
    ): Response<DeleteResponse>

    @DELETE("quiz/delete")
    suspend fun deleteQuiz(
        @Query("quiz_id") quizInt: Int
    ): Response<DeleteResponse>

    @DELETE("comment/delete")
    suspend fun deleteComment(
        @Query("comment_id") commentId: Int
    ): Response<DeleteResponse>

    @DELETE("reply/delete")
    suspend fun deleteReply(
        @Query("reply_id") replyId: Int
    ): Response<DeleteResponse>

    @DELETE("myvideo/delete")
    suspend fun deleteMyVideo(
        @Query("my_video_id") myVideoId: Int
    ): Response<DeleteResponse>

    @DELETE("mypost/delete")
    suspend fun deleteMyPost(
        @Query("my_post_id") myPostId: Int
    ): Response<DeleteResponse>
}
