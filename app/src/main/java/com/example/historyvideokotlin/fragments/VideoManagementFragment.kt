package com.example.historyvideokotlin.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.Gravity
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.VideoManagementAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentVideoManagementBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.VideoManagementViewModel

class VideoManagementFragment :
    BaseFragment<VideoManagementViewModel, FragmentVideoManagementBinding>() {
    override fun getLayoutId() = R.layout.fragment_video_management

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[VideoManagementViewModel::class.java]

    override fun initData() {
        binding.viewModel = viewModel
        binding.toolbar.title = "Danh sách video"
        viewModel.getVideoList()
        viewModel.videoList.observe(viewLifecycleOwner) { data ->
            data.let {
                binding.recyclerView.adapter =
                    VideoManagementAdapter(
                        it,
                        object : VideoManagementAdapter.ItemListener {
                            override fun onClick() {
                            }

                            override fun onEdit(video: Video) {
                                pushFragment(
                                    VideoEditFragment.newInstance(video),
                                    HistoryUtils.getSlideTransitionAnimationOptions()
                                )
                            }

                            override fun onDelete(videoId: Int) {
                                AlertDialog.Builder(requireContext())
                                    .setMessage("Bạn có chắc chắn muốn xóa")
                                    .setPositiveButton("Xóa") { dialog: DialogInterface, _: Int ->
                                        dialog.dismiss()
                                        viewModel.deleteVideo(videoId)
                                    }
                                    .setNegativeButton("Hủy") { dialog: DialogInterface, _: Int ->
                                        dialog.dismiss()
                                    }
                                    .create()
                                    .show()
                            }
                        }
                    )
            }
        }

        binding.toolbar.ivFilter.setOnClickListener {
            binding.drawer.openDrawer(Gravity.RIGHT)
        }

        binding.btnAdd.setOnClickListener {
            pushFragment(
                VideoEditFragment.newInstance(Video()),
                HistoryUtils.getSlideTransitionAnimationOptions()
            )
        }

        binding.ivAdd.setOnClickListener {
            pushFragment(
                VideoEditFragment.newInstance(Video()),
                HistoryUtils.getSlideTransitionAnimationOptions()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance() = VideoManagementFragment()
    }
}
