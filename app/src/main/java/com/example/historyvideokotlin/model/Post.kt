package com.example.historyvideokotlin.model

import java.io.Serializable

data class Post(
    var post_id: Int = 0,
    var post_type_id: Int = 0,
    var post_type_name: String = "",
    var theme_id: Int = 0,
    var title: String = "",
    var content: String = "",
    var pdf: String = "",
    var image: String = "",
    var description: String = "",
    var timeline: String = "",
    var place: String = "",
    var read_count: Int = 0,
    var download_count: Int = 0,
    var rate_count: Int = 0,
    var date_submitted: String = ""
) : Serializable
