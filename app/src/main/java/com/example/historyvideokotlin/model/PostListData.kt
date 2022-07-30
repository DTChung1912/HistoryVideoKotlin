package com.example.historyvideokotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class PostListData (
    @SerializedName("title")
    var title : String,
    @SerializedName("year")
    var year: String,
    @SerializedName("description")
    var description : String,
    @SerializedName("image")
    var image : String,
    @SerializedName("content")
    var content : String
    ) : Serializable