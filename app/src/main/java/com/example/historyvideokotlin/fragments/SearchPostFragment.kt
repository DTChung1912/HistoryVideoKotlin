package com.example.historyvideokotlin.fragments

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.KeywordAdapter
import com.example.historyvideokotlin.adapters.PostListAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentSearchPostBinding
import com.example.historyvideokotlin.dialogfragments.PostMoreDialogFragment
import com.example.historyvideokotlin.model.Keyword
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.SearchPostViewModel

class SearchPostFragment :
    BaseFragment<SearchPostViewModel, FragmentSearchPostBinding>(),
    PostListAdapter.OnItemClickListener,
    PostMoreDialogFragment.OnItemClickListener {

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_post
    }

    override fun getViewModel(): SearchPostViewModel =
        ViewModelProvider(requireActivity()).get(SearchPostViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        viewModel.setSearchVisible(true)
        viewModel.postList.observe(this, { data ->
            data.let {
                binding.recyclerSearchPost.adapter = PostListAdapter(it, requireContext(), this)
            }
        })

        viewModel.keywordList.observe(viewLifecycleOwner) {
            binding.recyclerKeyword.adapter =
                KeywordAdapter(
                    it,
                    object : KeywordAdapter.OnItemClickListener {
                        override fun onKeyword(keyword: Keyword) {
                            viewModel.setKeyword(keyword.content.toString())
                        }

                        override fun onDelete(keyword: Keyword) {
                            viewModel.deleteKeyword(keyword)
                            binding.edtSearch.setText("")
                        }

                        override fun onPaste(keyword: Keyword) {
                            binding.edtSearch.setText(keyword.content)
                        }
                    }
                )
        }

        binding.edtSearch.setOnClickListener {
            viewModel.setSearchVisible(true)
        }
        binding.edtSearch.setOnEditorActionListener(
            TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val keyword = Keyword(
                        keyword_id = null,
                        content = binding.edtSearch.text.toString(),
                        search_type = 0
                    )
                    viewModel.insertKeyword(keyword)
                    viewModel.setKeyword(binding.edtSearch.text.toString())
                    return@OnEditorActionListener true
                }
                false
            }
        )
        binding.ivBack.setOnClickListener {
            back()
        }
    }

    override fun onItemClick(postListData: Post) {
        pushFragment(
            PostDetailFragment.newInstance(postListData),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    override fun onMore(postId: Int) {
        PostMoreDialogFragment.newInstance(postId, this).show(parentFragmentManager, null)
    }

    override fun onLater(postId: Int) {
    }

    override fun onDownload(postId: Int) {
    }

    override fun onShare(postId: Int) {
    }

    override fun onDontCare(postId: Int) {
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance() = SearchPostFragment()
    }
}
