package com.example.historyvideokotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserListData (
    @SerializedName("userList")
    var userList: List<User>
) : Serializable