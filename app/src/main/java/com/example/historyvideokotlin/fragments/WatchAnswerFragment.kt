package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.QuizAnswerAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentWatchAnswerBinding
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.viewmodels.WatchAnswerViewModel
import java.util.*

class WatchAnswerFragment(val quizList: List<Quiz>) : BaseFragment<WatchAnswerViewModel,FragmentWatchAnswerBinding>() {

    private lateinit var adapter: QuizAnswerAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_watch_answer
    }

    override fun getViewModel(): WatchAnswerViewModel =
        ViewModelProvider(requireActivity()).get(WatchAnswerViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {

        val choosedAnswerList = arguments?.getStringArrayList(CHOOSED_ANSWER_LIST_KEY)
        setRecyclerView(quizList,choosedAnswerList!!)

    }

    private fun setRecyclerView(quizList: List<Quiz>, choosedAnswerList: List<String>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = QuizAnswerAdapter(quizList, requireContext(),choosedAnswerList )
        binding.recyclerWatchAnswer.setHasFixedSize(true)
        binding.recyclerWatchAnswer.layoutManager = linearLayoutManager
        binding.recyclerWatchAnswer.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    companion object {
        private const val CHOOSED_ANSWER_LIST_KEY = "CHOOSED_ANSWER_LIST_KEY"
        @JvmStatic
        fun newInstance(quizList : List<Quiz>,choosedAnswerList: List<String>) =
            WatchAnswerFragment(quizList).apply {
                arguments = bundleOf(
                    CHOOSED_ANSWER_LIST_KEY to choosedAnswerList
                )
            }
    }
}