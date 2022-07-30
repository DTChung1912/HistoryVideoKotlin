package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentResultStatisticBinding
import com.example.historyvideokotlin.viewmodels.ResultStatisticViewModel
import java.util.*

class ResultStatisticFragment : BaseFragment<ResultStatisticViewModel,FragmentResultStatisticBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_result_statistic
    }

    override fun getViewModel(): ResultStatisticViewModel =
        ViewModelProvider(requireActivity()).get(ResultStatisticViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }
}