package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.MyVideoAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyVideoBinding
import com.example.historyvideokotlin.dialogfragments.DeleteMyVideoBottomSheetDialogFragment
import com.example.historyvideokotlin.model.MyVideoType
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.MyVideoViewModel

class MyVideoFragment :
    BaseFragment<MyVideoViewModel, FragmentMyVideoBinding>(),
    MyVideoAdapter.OnItemClickListener,
    DeleteMyVideoBottomSheetDialogFragment.OnItemClickListener {

    private lateinit var adapter: MyVideoAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_video
    }

    override fun getViewModel(): MyVideoViewModel =
        ViewModelProvider(requireActivity()).get(MyVideoViewModel::class.java)

    override fun initData() {
        val myVideoType = arguments?.getInt(MY_VIDEO_TYPE_KEY)!!
        if (myVideoType < 4) {
            viewModel.getMyVideoListType(myVideoType)
            viewModel.myVideoResponse.observe(this) {
                adapter = MyVideoAdapter(it, myVideoType, requireContext(), this)
                binding.recylerMyVideo.adapter = adapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onMyVideoPlay(video: Video) {
        pushFragment(
            VideoDetailFragment.newInstance(video),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onMyVideoMore(myVideoId: Int, myVideoType: Int) {
        DeleteMyVideoBottomSheetDialogFragment.newInstance(myVideoId, myVideoType, this)
            .show(parentFragmentManager, null)
    }

    override fun onDelete(myVideoId: Int, myVideoType: Int) {
        when (myVideoType) {
            MyVideoType.LIKE.ordinal -> {
            }
            MyVideoType.LATER.ordinal -> {
            }
            MyVideoType.DOWNLOAD.ordinal -> {
            }
            MyVideoType.VIEW.ordinal -> {
            }
        }
    }

    companion object {
        const val MY_VIDEO_TYPE_KEY = "MY_VIDEO_TYPE_KEY"

        @JvmStatic
        fun newInstance(myVideoType: Int) =
            MyVideoFragment().apply {
                arguments = bundleOf(
                    MY_VIDEO_TYPE_KEY to myVideoType
                )
            }
    }
}
