package com.example.historyvideokotlin.utils

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import com.example.historyvideokotlin.HistoryApplication
import com.example.historyvideokotlin.R
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.FragNavTransactionOptions.Builder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object HistoryUtils {

    const val DATA_POST_ERROR = "DATA_POST_ERROR"

    fun getSlideTransitionAnimationOptions(): FragNavTransactionOptions {
        return Builder()
            .allowReordering(true)
            .customAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            )
            .build()
    }

    fun getSlideUpTransitionAnimationOptions(): FragNavTransactionOptions? {
        return Builder()
            .allowReordering(true)
            .customAnimations(
                R.anim.slide_up,
                R.anim.slide_down,
                R.anim.slide_down,
                R.anim.no_animation
            )
            .build()
    }

    fun getSlideNoAnimationOptions(): FragNavTransactionOptions {
        return FragNavTransactionOptions.Builder()
            .allowReordering(true)
            .build()
    }

    fun getDurationList(retriever: MediaMetadataRetriever, urlList: List<String>): List<String> {
        val durationList = mutableListOf<String>()
        for (i in 0 until urlList.size) {
            if (urlList[i].isNotEmpty()) {
                retriever.setDataSource(urlList[i], HashMap<String, String>())
                val time: String =
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)!!
                val timeInmillisec = time.toLong()
                val duration = timeInmillisec / 1000
                val hours = duration / 3600
                val minutes = (duration - hours * 3600) / 60
                val seconds = duration - (hours * 3600 + minutes * 60)

                if (hours > 0) {
                    durationList.add(String.format("%02d:%02d:%02d", hours, minutes, seconds))
                } else {
                    durationList.add(String.format("%02d:%02d", minutes, seconds))
                }
            } else {
                durationList.add("00:00")
            }
        }
        return durationList
    }

    fun getDuration(mediaPlayer: MediaPlayer, url: String): String {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return convertMillieToHMmSs(mediaPlayer.duration.toLong())
    }

    fun convertMillieToHMmSs(millie: Long): String {
        val seconds = millie / 1000
        val second = seconds % 60
        val minute = seconds / 60 % 60
        val hour = seconds / (60 * 60) % 24
        val result = ""
        return if (hour > 0) {
            String.format("%02d:%02d:%02d", hour, minute, second)
        } else {
            String.format("%02d:%02d", minute, second)
        }
    }

    fun getCurrentDate(): String {
        val date: Date = Calendar.getInstance().getTime()
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val strDate: String = dateFormat.format(date)
        return strDate
    }

    fun getCurrentTime(): String {
        val date: Date = Calendar.getInstance().getTime()
        val dateFormat: DateFormat = SimpleDateFormat("hh:mm:ss")
        val strDate: String = dateFormat.format(date)
        return strDate
    }

    fun getCurrentDateAndTime(): String {
        val date: Date = Calendar.getInstance().getTime()
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val strDate: String = dateFormat.format(date)
        return strDate
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

    fun saveDataToSharedPreference(key: Int, value: String?) {
        val editor: SharedPreferences.Editor = HistoryApplication.instance.getSharedPreferences(
            DATA_POST_ERROR,
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(key.toString(), value)
        editor.apply()
    }

    fun getDataErrorFromSharedPreference(keys: List<Int>): Map<Int, String?>? {
        val map: MutableMap<Int, String?> = java.util.HashMap()
        val prefs: SharedPreferences = HistoryApplication.instance.getSharedPreferences(
            DATA_POST_ERROR,
            Context.MODE_PRIVATE
        )
        for (i in keys.indices) {
            map[keys[i]] = prefs.getString(keys[i].toString(), "")
        }
        return map
    }

    fun deleteDataErrorFromSharedPreference(key: Int) {
        val editor: SharedPreferences.Editor = HistoryApplication.instance.getSharedPreferences(
            DATA_POST_ERROR,
            Context.MODE_PRIVATE
        ).edit()
        editor.remove(key.toString())
        editor.apply()
    }
}
