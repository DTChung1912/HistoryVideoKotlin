package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyPostResponse(
    val myPostStatusList: List<MyPostStatus>,
    val postList: List<Post>,
    val type: String,
    val size: Int
) : Serializable
