package com.example.historyvideokotlin.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseViewModel

class VideoInfoViewModel(application: Application) : BaseViewModel(application) {

    lateinit var isLiked : MutableLiveData<Boolean>

    fun setLiked(isliked : Boolean) {
        isLiked.value = isliked
    }

    fun getTabTitleIds(): List<Int> {
        val titleIds = mutableListOf<Int>()
        titleIds.run {
            add(R.string.title_video_tab_next_video)
            add(R.string.title_video_tab_commet)
        }
        return titleIds
    }
}