package com.example.historyvideokotlin.model

import java.io.Serializable

data class UpdateResponse(
    val isSuccess: Boolean,
    val data: String
) : Serializable
