package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.DownloadVideoAdapter
import com.example.historyvideokotlin.adapters.DownloadVideoListListener
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentDownloadListBinding
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.DownloadListViewModel

class DownloadListFragment : BaseFragment<DownloadListViewModel, FragmentDownloadListBinding>(),
    DownloadVideoListListener {

    private lateinit var adapter: DownloadVideoAdapter

    override fun getLayoutId() = R.layout.fragment_download_list

    override fun getViewModel() =
        ViewModelProvider(requireActivity()).get(DownloadListViewModel::class.java)

    override fun initData() {

        val myVideoType = arguments?.getInt(MY_VIDEO_TYPE_KEY)

        viewModel.readAllData.observe(viewLifecycleOwner, { videoLists ->
            MyLog.e("downloadvideolist>>>>>>>>>>>>",videoLists.toString())
            if (videoLists.isNotEmpty()) {
                setRecyclerView(videoLists,myVideoType!!)
            } else {
                binding.recylerMyVideo.visibility = GONE
                binding.tvEmptyList.visibility = VISIBLE
            }
        })

//        lifecycleScope.launch(Dispatchers.IO) {
//            val videoList = (activity as MainActivity).database!!.videoDao().getListVideo()
//            MyLog.e("Chung", videoList.toString())
//            setRecyclerView(videoList, myVideoType!!)
//        }
    }

    private fun setRecyclerView(downLoadVideoList: List<DownloadVideo>, myVideoType: Int) {
        adapter = DownloadVideoAdapter(downLoadVideoList, requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.recylerMyVideo.setHasFixedSize(true)
        binding.recylerMyVideo.layoutManager = linearLayoutManager
        binding.recylerMyVideo.adapter = adapter
    }

    override fun onItemClick(video: DownloadVideo) {
        pushFragment(
            DownloadDetailFragment.newInstance(video),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onMore() {

    }


    companion object {
        const val MY_VIDEO_TYPE_KEY = "MY_VIDEO_TYPE_KEY"

        fun newInstance(myVideoType: Int) = DownloadListFragment().apply {
            arguments = bundleOf(
                MY_VIDEO_TYPE_KEY to myVideoType
            )
        }
    }

}
