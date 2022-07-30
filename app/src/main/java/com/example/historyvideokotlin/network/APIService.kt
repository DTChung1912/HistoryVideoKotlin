package com.example.historyvideokotlin.network

import com.example.historyvideokotlin.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @GET("getUser.php")
    fun getUser(): Single<List<User>>

    @GET("getVideo.php")
    fun getVideo(): Single<List<Video>>

    @GET("getQuiz.php")
    fun getQuiz(): Single<List<Quiz>>

    @GET("getTheme.php")
    fun getTheme(): Single<List<PostTheme>>

    @GET("getPostPerson.php")
    fun getPostPerson(): Single<List<PostPerson>>

    @GET("getPostEvent.php")
    fun getPostEvent(): Single<List<PostEvent>>

    @GET("getPostPlace.php")
    fun getPostPlace(): Single<List<PostPlace>>

    @GET("getPostTimeline.php")
    fun getPostTimeline(): Single<List<PostTimeline>>

    @POST("postUser.php")
    fun postUser(
        @Body postUserRequest: PostUserRequest
    ): Completable

    @FormUrlEncoded
    @POST("postUser.php")
    fun postUser2(
        @Field("postName") postName : String,
        @Field("postEmail") postEmail : String
    ): Observable<List<User>>

    @FormUrlEncoded
    @POST("getComment.php")
    fun getComment(@Field("videoId") videoId : String): Observable<List<Comment>>

    @FormUrlEncoded
    @POST("getReply.php")
    fun getReply(@Field("commentId") commentId : String): Observable<List<Reply>>

    @GET("getViewedVideo.php")
    fun getViewedVideo(@Field("userId") userId : String,
                       @Field("videoId") videoId : String) : Single<List<Video>>

    @FormUrlEncoded
    @POST("updateViewedVideo.php")
    fun updateViewedVideo(@Field("userId") userId : String,
                          @Field("videoId") videoId : String) : Observable<Video>

    @FormUrlEncoded
    @POST("updateViewedVideo.php")
    fun updateStatusVideo(@Field("userId") userId : String,
                          @Field("videoId") videoId : String,
                          @Field("videoStatus") videoStatus : Int) : Observable<Video>
}