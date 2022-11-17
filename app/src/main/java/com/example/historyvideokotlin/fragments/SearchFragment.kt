package com.example.historyvideokotlin.fragments

import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentSearchBinding
import com.example.historyvideokotlin.viewmodels.SearchViewModel
import java.util.*

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun getViewModel(): SearchViewModel =
        ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)

    override fun initData() {
        val searchType = arguments?.getInt(SEARCH_TYPE_KEY)

        binding.run {
            edtSearch.setOnEditorActionListener(
                OnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        Toast.makeText(requireContext(), "chungOk" + edtSearch.text, Toast.LENGTH_LONG)
                            .show()
                        return@OnEditorActionListener true
                    }
                    false
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        const val SEARCH_TYPE_KEY = "KEYWORD_KEY"

        @JvmStatic
        fun newInstance(searchType: Int) =
            SearchFragment().apply {
                arguments = bundleOf(
                    SEARCH_TYPE_KEY to searchType
                )
            }
    }
}
