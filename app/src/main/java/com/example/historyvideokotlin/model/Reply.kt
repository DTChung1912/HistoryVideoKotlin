package com.example.historyvideokotlin.model

import java.io.Serializable

data class Reply(
    var reply_id: Int? = 0,
    var comment_id: Int = 0,
    var user_id: String = "",
    var user_name: String = "",
    var user_image: String = "",
    var content: String = "",
    var date_submitted: String = "",
    var like_count: Int = 0,
    var dislike_count: Int = 0
) : Serializable
