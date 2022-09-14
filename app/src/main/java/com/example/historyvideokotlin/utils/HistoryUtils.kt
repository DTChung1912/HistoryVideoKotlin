package com.example.historyvideokotlin.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.example.historyvideokotlin.HistoryApplication
import com.example.historyvideokotlin.R
import com.ncapdevi.fragnav.FragNavTransactionOptions

object HistoryUtils {

    fun getSlideTransitionAnimationOptions(): FragNavTransactionOptions {
        return FragNavTransactionOptions.Builder()
            .allowReordering(true)
            .customAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
            .build()
    }

    fun getSlideNoAnimationOptions(): FragNavTransactionOptions {
        return FragNavTransactionOptions.Builder()
            .allowReordering(true)
            .build()
    }

//    @SuppressLint("Range")
//    fun getContentType(uri: Uri?): String? {
//        var type: String? = null
//        val contentResolver: ContentResolver = HistoryApplication().getInstances().contentResolver
//        val cursor = contentResolver.query(uri!!, null, null, null, null)
//        try {
//            if (cursor != null) {
//                cursor.moveToFirst()
//                type = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE))
//                cursor.close()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return if (TextUtils.isEmpty(type)) "application/octet-stream" else type
//    }
}