package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentSearchBinding
import com.example.historyvideokotlin.viewmodels.SearchViewModel
import java.util.*

class SearchFragment : BaseFragment<SearchViewModel,FragmentSearchBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun getViewModel(): SearchViewModel =
        ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        val keyword = arguments?.getString(KEYWORD_KEY)
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    companion object {
        const val KEYWORD_KEY = "KEYWORD_KEY"
        @JvmStatic
        fun newInstance(keyword: String, ) =
            SearchFragment().apply {
                arguments = bundleOf(
                    KEYWORD_KEY to keyword
                )
            }
    }
}