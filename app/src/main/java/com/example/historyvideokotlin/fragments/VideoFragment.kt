package com.example.historyvideokotlin.fragments

import android.os.Environment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.adapters.VideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoBinding
import com.example.historyvideokotlin.dialogfragments.VideoMoreDialogFragment
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.VideoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class VideoFragment :
    BaseFragment<VideoViewModel, FragmentVideoBinding>(),
    VideoAdapter.OnItemClickListener,
    VideoMoreDialogFragment.OnItemClickListener {

    private var adapter: VideoAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_video

    override fun getViewModel(): VideoViewModel =
        ViewModelProvider(requireActivity()).get(VideoViewModel::class.java)

    

    override fun initData() {
        viewModel.getVideoData()
        viewModel.videoList.observe(this, { data ->
            data.let {
                setRecyclerView(it)
            }
        })
        binding.run {
            toolbar.tvTitle.text = "Video"
            toolbar.ivSearch.setOnClickListener {
                (activity as? MainActivity)?.onSearchClick(1)
            }
            toolbar.ivMenu.setOnClickListener {
                (activity as? MainActivity)?.onSettingClick()
            }
            btnTest.setOnClickListener {
//                lifecycleScope.launch(Dispatchers.IO) {
//                    val a = (activity as MainActivity).database!!.videoDao().getListVideo()
//                    MyLog.e("getDownloadVideo-------------",a.toString())
//                }
//                viewModel.getTest((activity as MainActivity).database!!)
                val direct = File(
                    Environment.getExternalStorageDirectory()
                        .toString() + "/HistoryVideo"
                )
//                if (!direct.exists()) {
//                    direct.mkdirs()
//                }
//                val files = direct.listFiles()
//                for (i in files) {
//                    val a = i.toURI()
//                }
//                val fileNames = arrayOfNulls<String>(files.size)
//                files?.mapIndexed { index, item ->
//                    fileNames[index] = item?.name
//                    MyLog.e("a" + index, fileNames[index])
//                }
            }
        }
    }

//    private fun setVideoDuration(urlList: List<String>) {
//        for (url in urlList) {
//            if (url.isNotEmpty()) {
//                exoPlayer.also {
//                    it.setMediaItem(MediaItem.fromUri(url))
//                    it.prepare()
//                    it.addListener(object : Player.Listener {
//                        override fun onPlaybackStateChanged(playbackState: Int) {
//                            super.onPlaybackStateChanged(playbackState)
//
//                            val millis: Long = it.duration
//                            val seconds = millis / 1000
//                            val second = seconds % 60
//                            val minute = seconds / 60 % 60
//                            val hour = seconds / (60 * 60) % 24
//                            var time = ""
//                            if (hour > 0) {
//                                time = String.format("%02d:%02d:%02d", hour, minute, second)
//                            } else {
//                                time = String.format("%02d:%02d", minute, second)
//                            }
//                            durationList.add(time)
//                        }
//                    })
//                }
//            } else {
//                durationList.add("00:00")
//            }
//        }
//    }

    private fun setRecyclerView(videoList: List<Video>) {
        adapter = VideoAdapter(videoList, requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.layoutManager = linearLayoutManager
        binding.myRecyclerView.adapter = adapter
    }

    

    override fun onResume() {
        super.onResume()
        showBottomMenu()
    }

    override fun onItemClick(video: Video) {
        pushFragment(
            VideoDetailFragment.newInstance(video),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
//        nextFragment(R.id.action_videoFragment_to_videoDetailFragment,VideoDetailFragment.newInstance(video))
    }

    override fun onMore(videoId: Int) {
        VideoMoreDialogFragment.newInstance(videoId, this).show(parentFragmentManager, null)
    }

    override fun onLater(videoId: Int) {
//        viewModel.updateLaterVideo(videoId, 1)
    }

    override fun onDownload(videoId: Int) {
//        viewModel.updateDownloadMyVideo(videoId, 1)
        viewModel.updateVideoDownload(videoId)
    }

    override fun onShare(videoId: Int) {
//        viewModel.updateShareVideo(videoId, 1)
    }

    override fun onDontCare(videoId: Int) {
//        viewModel.updateDontCareVideo(videoId, 1)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            VideoFragment()
    }
}
