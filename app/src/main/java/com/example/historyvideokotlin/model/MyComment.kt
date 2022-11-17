package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyComment(
    val my_comment_id: Int? = 0,
    var user_id: String? = "",
    var video_id: Int? = 0,
    var comment_id: Int? = 0,
    var isLike: Int? = -1
) : Serializable
