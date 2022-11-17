package com.example.historyvideokotlin.model

data class VideoStatus(
    var like: Boolean? = false,
    var dislike: Boolean? = false,
    var download: Boolean? = false,
    var later: Boolean? = false
)
