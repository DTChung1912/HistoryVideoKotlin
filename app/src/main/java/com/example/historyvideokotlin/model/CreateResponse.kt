package com.example.historyvideokotlin.model

import java.io.Serializable

data class CreateResponse(
    val isSuccess: Boolean,
    val data: String
) : Serializable