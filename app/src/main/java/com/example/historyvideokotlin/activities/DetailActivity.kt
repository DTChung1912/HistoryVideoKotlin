package com.example.historyvideokotlin.activities

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseActivity
import com.example.historyvideokotlin.databinding.ActivityDetailBinding
import com.example.historyvideokotlin.fragments.*
import com.example.historyvideokotlin.model.PostListData
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.Constants.DETAIL_KEY
import com.example.historyvideokotlin.utils.Constants.POST_DATA_KEY
import com.example.historyvideokotlin.utils.Constants.POST_DETAIL_KEY
import com.example.historyvideokotlin.utils.Constants.QUIZ_DATA_KEY
import com.example.historyvideokotlin.utils.Constants.VIDEO_DATA_KEY
import com.example.historyvideokotlin.utils.Constants.VIDEO_DETAIl_KEY
import com.example.historyvideokotlin.viewmodels.DetailViewModel
import java.util.*

class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>() {

    private var detailKey: Int? = null
    private  var postListData : PostListData? = null
    private var video : Video? = null
    private var post_theme_id : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setScreenNoLimit()

        detailKey = intent.getIntExtra(DETAIL_KEY, 0)
        postListData = intent.getSerializableExtra(POST_DATA_KEY) as PostListData?
        video = intent.getSerializableExtra(VIDEO_DATA_KEY) as Video?
        post_theme_id = intent.getIntExtra(QUIZ_DATA_KEY, 0)
        when (detailKey) {
            POST_DETAIL_KEY -> {
                replaceFragment(R.id.fragmentContainer, PostDetailFragment.newInstance(postListData!!) , false, null)
            }
            VIDEO_DETAIl_KEY -> {
                replaceFragment(R.id.fragmentContainer, VideoDetailFragment.newInstance(video!!), false, null)
            }
            else -> {
                replaceFragment(R.id.fragmentContainer, QuizDetailFragment.newInstance(post_theme_id), false, null)
            }
        }


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun getViewModelClass(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val fragmentList: List<Fragment> = supportFragmentManager.fragments
            for (f in fragmentList) {
                if (f is ReplyFragment) {
                    replaceFragment(R.id.fragmentContainerVideoDetail, CommentFragment.newInstance(
                        video!!.video_id), false, null)
                }
            }
        }
    }

}