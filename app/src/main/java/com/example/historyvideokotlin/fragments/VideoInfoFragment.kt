package com.example.historyvideokotlin.fragments

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoInfoBinding
import com.example.historyvideokotlin.dialogfragments.LoginDialogFragment
import com.example.historyvideokotlin.dialogfragments.OnLoginItemClickListener
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.VideoInfoViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File
import java.util.*

class VideoInfoFragment : BaseFragment<VideoInfoViewModel, FragmentVideoInfoBinding>() {
//    private var video: Video? = null

    private val PERMISSION_REQUEST_CODE = 1234
    private val urlsAdded = ArrayList<String>()
    private lateinit var video: Video
    private var myDownloaded: Long = 0
    private lateinit var downloadManager: DownloadManager

    private var isLikeCheck = false
    private var isDislikeCheck = false

    private var permission = 0
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                permission = 1
            } else {
                permission = 0
            }
        }

    override fun getLayoutId(): Int {
        return R.layout.fragment_video_info
    }

    override fun getViewModel(): VideoInfoViewModel =
        ViewModelProvider(requireActivity()).get(VideoInfoViewModel::class.java)

    

    override fun initData() {
        downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        video = arguments?.getSerializable(VIDEO_KEY) as Video
        val titleIds = viewModel.getTabTitleIds()

        var myVideoId = 0

        viewModel.updateViewedMyVideo(video.video_id)
        viewModel.updateViewCountVideo(video.video_id)
        viewModel.getMyVideo(video.video_id)
        viewModel.myVideoList.observe(this, { data ->
            data.let {

                if (it[0].isLike != null) {
                    var isLike = it[0].isLike
                    myVideoId = it[0].my_video_id
                    if (isLike == 1) {
                        isLikeCheck = true
                        binding.ivLike.setImageResource(R.drawable.liked)
                    } else {
                        isDislikeCheck = true
                        binding.ivDislike.setImageResource(R.drawable.disliked)
                    }
                } else {
                    binding.ivLike.setImageResource(R.drawable.like_video)
                    binding.ivDislike.setImageResource(R.drawable.dislike_video)
                }
            }
        })

        binding.run {

            tvVideoTitle.text = video.title
            tvViewCount.text = video.view_count.toString() + " lượt xem"
            tvCreaterName.text = video.creater
            tvDateSubmitted.text = video.date_submitted

            viewPager.adapter =
                PagerAdapter(
                    this@VideoInfoFragment,
                    video.video_id,
                    video.theme_id
                )

            if (!HistoryUserManager.instance.checkUserLogined()) {
                ivLike.setOnClickListener {
                    showLoginDialog()
                }

                ivDislike.setOnClickListener {
                    showLoginDialog()
                }

                ivSave.setOnClickListener {
                    showLoginDialog()
                }
            } else {
                ivLike.setOnClickListener {
                    isLikeCheck = true
                    isDislikeCheck = false
                    ivDislike.setImageResource(R.drawable.dislike_video)
                }

                ivDislike.setOnClickListener {
                    isLikeCheck = false
                    isDislikeCheck = true

                    ivLike.setImageResource(R.drawable.like_video)
                    viewModel.deleteLikeMyVideo(myVideoId)
                }

                ivSave.setOnClickListener {
                }
            }

            ivDownload.setOnClickListener {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (permission == 1) {
                    showToast("Bắt đầu download " + video.title)
                    downloadVideo(video.video_url, video.title)
                }
            }
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titleIds[position])
        }.attach()

        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                if (id == myDownloaded) {
                    showToast(video.title + " download thành công")
                    val url = downloadManager.getUriForDownloadedFile(id).toString()
                    val downloadVideo = DownloadVideo(
                        video_id = null,
                        title = video.title,
                        theme_id = video.theme_id,
                        creater_image = video.creater_image,
                        creater = video.creater,
                        platform = video.platform,
                        like_count = video.like_count,
                        view_count = video.view_count,
                        dislike_count = video.dislike_count,
                        comment_count = video.comment_count,
                        share_count = video.share_count,
                        video_url = url,
                        duration = video.duration,
                        date_submitted = video.date_submitted
                        )
//                    viewModel.downloadVideo((activity as MainActivity).database!!, downloadVideo)
                    MyLog.e("filename", url)
                } else {
                    showToast(video.title + " download thất bại")
                }
            }
        }

        requireContext().registerReceiver(
            br,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    fun downloadVideo(url: String, title: String) {
        try {
            val direct = File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/HistoryVideo"
            )
            if (!direct.exists()) {
                direct.mkdirs()
            }
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle(title + ".mp4")
                .setDescription("${title} is downloading....")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
                .setVisibleInDownloadsUi(false)

            request.setDestinationInExternalPublicDir(
                "/HistoryVideo",
                title + ".mp4"
            )
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

            myDownloaded = downloadManager.enqueue(request)
            request.setDestinationInExternalFilesDir(
                requireContext().applicationContext,
                "/file",
                "Question1.mp4"
            )
        } catch (e: Exception) {
            MyLog.e("Video_error", e.message)
            showToast(e.message.toString())
        }
    }

    private fun showLoginDialog() {
        LoginDialogFragment.newInstance(object : OnLoginItemClickListener {
            override fun onLogin() {
                pushFragment(
                    HistoryLoginFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }

            override fun onRegister() {
                pushFragment(
                    RegisterFragment.newInstance(),
                    HistoryUtils.getSlideTransitionAnimationOptions()
                )
            }

        }).show(parentFragmentManager, null)
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    private class PagerAdapter(
        fragment: Fragment,
        val video_id: Int,
        val theme_id: Int
    ) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> NextVideoFragment.newInstance(theme_id)
                else -> CommentFragment.newInstance(video_id)
            }
        }
    }

    

    companion object {
        const val VIDEO_KEY = "VIDEO_KEY"

        @JvmStatic
        fun newInstance(video: Video) =
            VideoInfoFragment().apply {
                arguments = bundleOf(
                    VIDEO_KEY to video
                )
            }
    }
}
