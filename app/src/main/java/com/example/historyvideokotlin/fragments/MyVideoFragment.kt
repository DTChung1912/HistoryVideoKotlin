package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.adapters.MyVideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.data.VideoDatabase
import com.example.historyvideokotlin.databinding.FragmentMyVideoBinding
import com.example.historyvideokotlin.dialogfragments.DeleteMyVideoBottomSheetDialogFragment
import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.MyVideoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
                setRecyclerView(it, myVideoType)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    private fun setRecyclerView(myVideoRespone: MyVideoRespone, myVideoType: Int) {
        adapter = MyVideoAdapter(myVideoRespone, myVideoType, requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.recylerMyVideo.setHasFixedSize(true)
        binding.recylerMyVideo.layoutManager = linearLayoutManager
        binding.recylerMyVideo.adapter = adapter
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
            MyVideoType.LIKE.id -> {
            }
            MyVideoType.LATER.id -> {
            }
            MyVideoType.DOWNLOAD.id -> {
            }
            MyVideoType.VIEW.id -> {
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
