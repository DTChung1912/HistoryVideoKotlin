package com.example.historyvideokotlin.model

import com.google.gson.annotations.SerializedName

data class CommentRequest(
    @field:SerializedName("comment_id")
    val comment_id: Int? = null,
    @field:SerializedName("video_id")
    val video_id: Int? = null,
    @field:SerializedName("user_id")
    val user_id: String? = null,
    @field:SerializedName("content")
    val content: String? = null,
    @field:SerializedName("date_submitted")
    val date_submitted: String? = null,
    @field:SerializedName("reply_count")
    val reply_count: Int? = null,
    @field:SerializedName("like_count")
    val like_count: Int? = null,
    @field:SerializedName("dislike_count")
    val dislike_count: Int? = null
)
