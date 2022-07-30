package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuestionBinding
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.viewmodels.QuestionViewModel
import java.util.*

class QuestionFragment : BaseFragment<QuestionViewModel, FragmentQuestionBinding>() {

    private var quiz : Quiz? = null
    private var ramdomAnswerList : MutableList<String>? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_question
    }

    override fun getViewModel(): QuestionViewModel =
        ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        quiz = arguments?.getSerializable(QUIZ_DATA_KEY) as Quiz
        ramdomAnswerList = arguments?.getStringArrayList(RANDOM_ANSWER_LIST_KEY)

        binding.run {
            if (!quiz!!.image.isNullOrEmpty()) {
                Glide.with(requireContext()).load(quiz!!.image).into(ivImageQuiz)
            }
            tvQuiz.text = quiz!!.question
            tvAnswerA.text = ramdomAnswerList!![0]
            tvAnswerB.text = ramdomAnswerList!![1]
            tvAnswerC.text = ramdomAnswerList!![2]
            tvAnswerD.text = ramdomAnswerList!![3]
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        const val QUIZ_DATA_KEY = "QUIZ_DATA_KEY"
        const val RANDOM_ANSWER_LIST_KEY = "RANDOM_ANSWER_LIST_KEY"

        @JvmStatic
        fun newInstance(quiz: Quiz, ramdomAnswerList : MutableList<String>) =
            QuestionFragment().apply {
                arguments = bundleOf(
                    QUIZ_DATA_KEY to quiz,
                    RANDOM_ANSWER_LIST_KEY to ramdomAnswerList
                )
            }
    }
}