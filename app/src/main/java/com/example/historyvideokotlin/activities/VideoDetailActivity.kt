package com.example.historyvideokotlin.activities

import android.os.Bundle
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseActivity
import com.example.historyvideokotlin.databinding.ActivityVideoDetailBinding
import com.example.historyvideokotlin.viewmodels.VideoDetailViewModel
import java.util.*

class VideoDetailActivity : BaseActivity<VideoDetailViewModel, ActivityVideoDetailBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setScreenNoLimit()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_video_detail
    }

    override fun getViewModelClass(): Class<VideoDetailViewModel> {
        return VideoDetailViewModel::class.java
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}