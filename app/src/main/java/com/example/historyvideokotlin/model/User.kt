package com.example.historyvideokotlin.model

import java.io.Serializable

data class User (
    val user_id: String,

    val user_name: String,

    val user_image: String,

    val email: String,

    val birthday : String,

    val phone_number: String,

    val address : String,

    val last_active: String,

    val account_type_id : Int
) : Serializable