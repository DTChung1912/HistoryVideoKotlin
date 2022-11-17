package com.example.historyvideokotlin.model

import java.io.Serializable

data class Theme(
    val theme_id: Int,
    val theme_name: String,
    val theme_image: String
) : Serializable
