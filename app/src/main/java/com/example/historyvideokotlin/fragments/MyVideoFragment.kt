package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.MyVideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyVideoBinding
import com.example.historyvideokotlin.dialogfragments.DeleteMyVideoBottomSheetDialogFragment
import com.example.historyvideokotlin.model.MyVideo
import com.example.historyvideokotlin.model.MyVideoListData
import com.example.historyvideokotlin.viewmodels.MyVideoViewModel
import java.util.*

class MyVideoFragment : BaseFragment<MyVideoViewModel, FragmentMyVideoBinding>(),
    MyVideoAdapter.OnItemClickListener, DeleteMyVideoBottomSheetDialogFragment.OnItemClickListener {

    private lateinit var adapter: MyVideoAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_video
    }

    override fun getViewModel(): MyVideoViewModel =
        ViewModelProvider(requireActivity()).get(MyVideoViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        val myVideoListData = arguments?.getSerializable(MY_VIDEO_LIST_KEY) as MyVideoListData
        val myVideoType = arguments?.getInt(MY_VIDEO_TYPE_KEY)
        var myVideoList = myVideoListData.myVideoList
        val isLikeList = mutableListOf<MyVideo>()
        val isLaterList = mutableListOf<MyVideo>()
        val isDownloadList = mutableListOf<MyVideo>()
        val isViewedList = mutableListOf<MyVideo>()
        val isShareList = mutableListOf<MyVideo>()

        for (i in 0 until myVideoList.size) {
            val myVideo = myVideoList[i]
            if (myVideo.isLike == 1) {
                isLikeList.add(myVideo)
            }
            if (myVideo.isLater == 1) {
                isLaterList.add(myVideo)
            }
            if (myVideo.isDownload == 1) {
                isDownloadList.add(myVideo)
            }
            if (myVideo.isView == 1) {
                isViewedList.add(myVideo)
            }
            if (myVideo.isShare == 1) {
                isShareList.add(myVideo)
            }
        }

        when (myVideoType) {
            MyVideoType.Like.id -> {
                setRecyclerView(isLikeList,MyVideoType.Like.id)
            }
            MyVideoType.Later.id -> {
                setRecyclerView(isLaterList,MyVideoType.Later.id)
            }
            MyVideoType.Download.id -> {
                setRecyclerView(isDownloadList,MyVideoType.Download.id)
            }
            MyVideoType.View.id -> {
                setRecyclerView(isViewedList,MyVideoType.View.id)
            }
        }
    }

    private fun setRecyclerView(myVideoList: List<MyVideo>,myVideoType: Int) {
        adapter = MyVideoAdapter(myVideoList, myVideoType , requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.recylerMyVideo.setHasFixedSize(true)
        binding.recylerMyVideo.layoutManager = linearLayoutManager
        binding.recylerMyVideo.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {
        const val MY_VIDEO_LIST_KEY = "MY_VIDEO_LIST_KEY"
        const val MY_VIDEO_TYPE_KEY = "MY_VIDEO_TYPE_KEY"

        @JvmStatic
        fun newInstance(myVideoListData: MyVideoListData, myVideoType: Int) =
            MyVideoFragment().apply {
                arguments = bundleOf(
                    MY_VIDEO_LIST_KEY to myVideoListData,
                    MY_VIDEO_TYPE_KEY to myVideoType
                )
            }
    }

    enum class MyVideoType(val id: Int) {
        Like(1),
        Later(2),
        Download(3),
        View(4),
        Share(5)
    }

    override fun onMyVideoPlay() {
//        pushFragment(VideoDetailFragment.newInstance())
    }

    override fun onMyVideoMore(myVideoId: Int,myVideoType: Int) {
        DeleteMyVideoBottomSheetDialogFragment.newInstance(myVideoId,myVideoType,this).show(parentFragmentManager,null)
    }

    override fun onDelete(myVideoId: Int,myVideoType: Int) {
        when (myVideoType) {
            MyVideoType.Like.id -> {
                viewModel.deleteLikeMyVideo(myVideoId)
            }
            MyVideoType.Later.id -> {
                viewModel.deleteLaterMyVideo(myVideoId)
            }
            MyVideoType.Download.id -> {
                viewModel.deleteDownloadMyVideo(myVideoId)
            }
            MyVideoType.View.id -> {
                viewModel.deleteViewMyVideo(myVideoId)
            }
        }
    }
}