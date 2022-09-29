package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyVideoRespone(
    val myVideoStatusList: List<MyVideoStatus>,
    val videoList: List<Video>,
    val type: String,
    val size: Int
) : Serializable
