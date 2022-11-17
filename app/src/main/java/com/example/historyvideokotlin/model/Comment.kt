package com.example.historyvideokotlin.model

import java.io.Serializable

data class Comment(
    var comment_id: Int = 0,
    var video_id: Int? = 0,
    var user_id: String? = "",
    var user_name: String? = "",
    var user_image: String? = "",
    var content: String? = "",
    var date_submitted: String? = "",
    var reply_count: Int = 0,
    var like_count: Int = 0,
    var dislike_count: Int = 0
) : Serializable
