package com.example.historyvideokotlin.model

import com.google.gson.annotations.SerializedName

data class PostUserRequest(
    @SerializedName("postName")
    val postName: String,
    @SerializedName("postEmail")
    val postEmail: String
)