package com.example.historyvideokotlin.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.PostManagementAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostManagementBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.PostManagementViewModel

class PostManagementFragment :
    BaseFragment<PostManagementViewModel, FragmentPostManagementBinding>() {
    override fun getLayoutId() = R.layout.fragment_post_management

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[PostManagementViewModel::class.java]

    override fun initData() {
        binding.toolbar.title = "Danh Sách Bài Viết"

        binding.viewModel = viewModel
        viewModel.getPostList()
        viewModel.postList.observe(viewLifecycleOwner) { data ->
            data.let {
                binding.recyclerView.adapter = PostManagementAdapter(
                    it,
                    object : PostManagementAdapter.ItemListener {
                        override fun onEdit(post: Post) {
                            pushFragment(
                                PostEditFragment.newInstance(post),
                                HistoryUtils.getSlideTransitionAnimationOptions()
                            )
                        }

                        override fun onDelete(postId: Int) {
                            AlertDialog.Builder(requireContext())
                                .setMessage("Bạn có chắc chắn muốn xóa")
                                .setPositiveButton("Xóa") { dialog: DialogInterface, _: Int ->
                                    dialog.dismiss()
                                    viewModel.deletePost(postId)
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

        binding.btnAdd.setOnClickListener {
            pushFragment(
                PostEditFragment.newInstance(Post()),
                HistoryUtils.getSlideTransitionAnimationOptions()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance() = PostManagementFragment()
    }
}
