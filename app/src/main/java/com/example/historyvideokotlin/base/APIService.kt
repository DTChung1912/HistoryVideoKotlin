package com.example.historyvideokotlin.base

import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.model.Video
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("getUser.php")
    fun getUsers(): Call<User>

    @GET("getUser.php")
    fun getUser(): Single<List<User>>

    @GET("getPost.php")
    fun getPost(): Single<List<Post>>

    @GET("getVideo.php")
    fun getVideo(): Single<List<Video>>

    @GET("getQuiz.php")
    fun getQuiz(): Single<List<Quiz>>
}