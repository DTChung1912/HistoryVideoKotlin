package com.example.historyvideokotlin.model

import java.io.Serializable

data class CountResponse(
    val isSuccess: Boolean,
    val data: Int
) : Serializable
