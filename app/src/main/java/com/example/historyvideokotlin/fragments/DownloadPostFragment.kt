package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.DownloadPostAdapter
import com.example.historyvideokotlin.adapters.DownloadPostListListener
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentDownloadPostBinding
import com.example.historyvideokotlin.model.DownloadPost
import com.example.historyvideokotlin.viewmodels.DownloadListViewModel

class DownloadPostFragment :
    BaseFragment<DownloadListViewModel, FragmentDownloadPostBinding>(),
    DownloadPostListListener {

    override fun getLayoutId() = R.layout.fragment_download_post

    override fun getViewModel() =
        ViewModelProvider(requireActivity()).get(DownloadListViewModel::class.java)

    override fun initData() {
        viewModel.postList.observe(this) { postLists ->
            if (!postLists.isNullOrEmpty()) {
                binding.recyclerView.adapter =
                    DownloadPostAdapter(postLists, requireContext(), this)
            }
        }

        binding.ivBack.setOnClickListener {
            back()
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onItemClick(post: DownloadPost) {
    }

    override fun onMore() {
    }

    companion object {
        fun newInstance() = DownloadPostFragment()
    }
}
