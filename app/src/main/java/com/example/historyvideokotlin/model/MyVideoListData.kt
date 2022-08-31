package com.example.historyvideokotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyVideoListData (
    @SerializedName("videoList")
    var myVideoList: List<MyVideo>
): Serializable