package com.example.historyvideokotlin.viewmodels

import android.app.Application
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseViewModel

class VideoDetailViewModel(application: Application) : BaseViewModel(application) {
    fun getTabTitleIds(): List<Int> {
        val titleIds = mutableListOf<Int>()
        titleIds.run {
            add(R.string.title_video_tab_next_video)
            add(R.string.title_video_tab_commet)
        }
        return titleIds
    }
}
