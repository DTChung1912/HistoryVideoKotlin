package com.example.historyvideokotlin.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "keyword_table")
data class Keyword(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "keyword_id")
    var keyword_id: Int? = null,
    @ColumnInfo(name = "content")
    var content: String? = null,
    @ColumnInfo(name = "search_type")
    var search_type: Int? = null
) : Parcelable