package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.MyPostAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPostBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.MyPostViewModel
import java.util.*

class MyPostFragment :
    BaseFragment<MyPostViewModel, FragmentMyPostBinding>(),
    MyPostAdapter.OnItemClickListener {

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_post
    }

    override fun getViewModel(): MyPostViewModel =
        ViewModelProvider(requireActivity()).get(MyPostViewModel::class.java)

    override fun initData() {
        viewModel.getData()
        viewModel.myPostResponse.observe(this) { data ->
            data.let {
                MyLog.e("testtttttt", it.toString())
                binding.recyclerView.adapter = MyPostAdapter(it, requireContext(), this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onMyPostClick(post: Post) {
        pushFragment(
            PostDetailFragment.newInstance(post),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onMyPostMore(myPostId: Int) {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyPostFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
