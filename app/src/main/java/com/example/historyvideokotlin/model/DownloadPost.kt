package com.example.historyvideokotlin.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "post_table")
data class DownloadPost (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    var post_id: Int? = null,
    @ColumnInfo(name = "post_type_id")
    var post_type_id: String? = null,
    @ColumnInfo(name = "post_type_name")
    var post_type_name: String? = null,
    @ColumnInfo(name = "theme_id")
    var theme_id: Int? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "content")
    var content: String? = null,
    @ColumnInfo(name = "pdf")
    var pdf: String? = null,
    @ColumnInfo(name = "image")
    var image: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "timeline")
    var timeline: String? = null,
    @ColumnInfo(name = "place")
    var place: String? = null,
    @ColumnInfo(name = "read_count")
    var read_count: Int? = null,
    @ColumnInfo(name = "download_count")
    var download_count: Int? = null,
    @ColumnInfo(name = "rate_count")
    var rate_count: Int? = null,
    @ColumnInfo(name = "date_submitted")
    var date_submitted: String
) : Parcelable