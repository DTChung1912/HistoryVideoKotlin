package com.example.historyvideokotlin.fragments

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.MyPostAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPostBinding
import com.example.historyvideokotlin.model.MyPostResponse
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.MyPostViewModel
import java.util.*

class MyPostFragment :
    BaseFragment<MyPostViewModel, FragmentMyPostBinding>(),
    MyPostAdapter.OnItemClickListener {

    private var adapter: MyPostAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_post
    }

    override fun getViewModel(): MyPostViewModel =
        ViewModelProvider(requireActivity()).get(MyPostViewModel::class.java)

    

    override fun initData() {
        viewModel.getData()
        viewModel.myPostResponse.observe(this) {
            setRecyclerView(it)
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    

    private fun setRecyclerView(myPostResponse: MyPostResponse) {
        adapter = MyPostAdapter(myPostResponse, requireContext(), this)
        val linearLayoutManager = LinearLayoutManager(view?.context)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyPostFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onMyPostClick(post: Post) {
        pushFragment(
            PostDetailFragment.newInstance(post),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onMyPostMore(myPostId: Int) {
    }
}
