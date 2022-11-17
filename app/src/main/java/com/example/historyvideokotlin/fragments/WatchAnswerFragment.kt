package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.WatchAnswerAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentWatchAnswerBinding
import com.example.historyvideokotlin.model.AnswerModel
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.SelectAnswer
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.WatchAnswerViewModel

class WatchAnswerFragment : BaseFragment<WatchAnswerViewModel, FragmentWatchAnswerBinding>() {
    private lateinit var quizList: List<Quiz>
    private lateinit var selectList: List<SelectAnswer>
    private lateinit var answerList: List<AnswerModel>

    override fun getLayoutId(): Int {
        return R.layout.fragment_watch_answer
    }

    override fun getViewModel(): WatchAnswerViewModel =
        ViewModelProvider(requireActivity()).get(WatchAnswerViewModel::class.java)

    override fun initData() {
        MyLog.e("selectList", selectList.toString())
        MyLog.e("quizList", quizList.toString())

        binding.recyclerWatchAnswer.adapter =
            WatchAnswerAdapter(quizList, requireContext(), selectList, answerList)
        binding.ivBack.setOnClickListener {
            back()
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance(
            quizList: List<Quiz>,
            selectList: List<SelectAnswer>,
            answerlist: List<AnswerModel>
        ) =
            WatchAnswerFragment().apply {
                this.quizList = quizList
                this.selectList = selectList
                this.answerList = answerlist
            }
    }
}
