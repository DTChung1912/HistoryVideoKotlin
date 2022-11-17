package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyVideoStatus(
    val my_video_id: Int? = 0,
    var user_id: String? = null,
    var video_id: Int? = null,
    var isLike: Int? = null,
    var isLater: Int? = null,
    var isDownload: Int? = null,
    var isView: Int? = null,
    var isShare: Int? = null,
    var isDontCare: Int? = null,
    var view_time: Int? = null,
    var duration: Int? = null
) : Serializable
