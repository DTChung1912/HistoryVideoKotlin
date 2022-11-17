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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
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

class VideoInfoFragment : BaseFragment<VideoInfoViewModel, FragmentVideoInfoBinding>() {
//    private var video: Video? = null

    private val PERMISSION_REQUEST_CODE = 1234
    private val urlsAdded = ArrayList<String>()
    private lateinit var video: Video
    private var myDownloaded: Long = 0
    private lateinit var downloadManager: DownloadManager

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
        binding.viewModel = viewModel
        downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        video = arguments?.getSerializable(VIDEO_KEY) as Video
        viewModel.setVideo(video)
        val titleIds = viewModel.getTabTitleIds()

        binding.ivLike.setOnClickListener {
            if (!isLogged()) {
                showLoginDialog()
            } else {
                viewModel.setLiked(video.video_id)
            }
        }

        binding.ivDislike.setOnClickListener {
            if (!isLogged()) {
                showLoginDialog()
            } else {
                viewModel.setDisliked(video.video_id)
            }
        }

        binding.ivLater.setOnClickListener {
            if (!isLogged()) {
                showLoginDialog()
            } else {
                viewModel.setLatered(true)
            }
        }

        binding.ivLiked.setOnClickListener {
            if (!isLogged()) {
                showLoginDialog()
            } else {
                viewModel.cancelLike(video.video_id)
            }
        }

        binding.ivDisliked.setOnClickListener {
            if (!isLogged()) {
                showLoginDialog()
            } else {
                viewModel.cancelDislike(video.video_id)
            }
        }

        binding.ivLatered.setOnClickListener {
            if (!isLogged()) {
                showLoginDialog()
            } else {
                viewModel.setLatered(false)
            }
        }

        binding.ivDownload.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permission == 1) {
                showToast("Bắt đầu download " + video.title)
                downloadVideo(video.video_url, video.title)
            }
        }

        binding.viewPager.adapter =
            PagerAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                this@VideoInfoFragment,
                video
            )

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titleIds[position])
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()

        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                if (id == myDownloaded) {
                    viewModel.setDownload(true)
                    showToast(video.title + " download thành công")
                    viewModel.setDownload(true)
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
                        download_count = video.download_count,
                        video_url = url,
                        duration = video.duration,
                        date_submitted = video.date_submitted
                    )
                    viewModel.downloadVideo(downloadVideo)
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
                .setDescription("$title is downloading....")
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
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragment: Fragment,
        val video: Video
    ) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> NextVideoFragment.newInstance(video.video_id, video.theme_id)
                else -> CommentFragment.newInstance(video)
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
