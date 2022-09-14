package com.example.historyvideokotlin.model

import java.io.Serializable

data class DeleteResponse(
    val isSuccess: Boolean,
    val data: String
) : Serializable