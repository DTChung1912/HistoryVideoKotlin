package com.example.historyvideokotlin.model

import java.io.Serializable

data class Video(
    var video_id: Int = 0,
    var title: String = "",
    var theme_id: Int = 0,
    var creater_image: String = "",
    var creater: String = "",
    var platform: String = "",
    var like_count: Int = 0,
    var view_count: Int = 0,
    var dislike_count: Int = 0,
    var comment_count: Int = 0,
    var download_count: Int = 0,
    var video_url: String = "",
    var poster_image: String = "",
    var duration: Int = 0,
    var date_submitted: String = ""
) : Serializable
