package com.example.historyvideokotlin.fragments

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.component.SelectLayout
import com.example.historyvideokotlin.databinding.FragmentQuestionBinding
import com.example.historyvideokotlin.model.AnswerTitle
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.viewmodels.QuestionViewModel
import java.util.*

class QuestionFragment :
    BaseFragment<QuestionViewModel, FragmentQuestionBinding>() {

    private var isCorrect = false
    private var quiz: Quiz? = null

    private lateinit var onClickListener: OnClickListener
    private var position: Int = 0
    private var cancelAnswer = -1

    override fun getLayoutId(): Int {
        return R.layout.fragment_question
    }

    override fun getViewModel(): QuestionViewModel =
        ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        quiz = arguments?.getSerializable(QUIZ_DATA_KEY) as Quiz
        viewModel.setQuiz(quiz!!)
        position = arguments?.getInt(POSITION_KEY)!!

        binding.number = position + 1
        binding.tvAnswerA.setOnClickListener {
            (it.parent as SelectLayout).onClick(it)
            viewModel.ansA.observe(viewLifecycleOwner) {
                updateAnswer(it, AnswerTitle.A.title)
            }
        }

        binding.tvAnswerB.setOnClickListener {
            (it.parent as SelectLayout).onClick(it)
            viewModel.ansB.observe(viewLifecycleOwner) {
                updateAnswer(it, AnswerTitle.B.title)
            }
        }

        binding.tvAnswerC.setOnClickListener {
            (it.parent as SelectLayout).onClick(it)
            viewModel.ansC.observe(viewLifecycleOwner) {
                updateAnswer(it, AnswerTitle.C.title)
            }
        }

        binding.tvAnswerD.setOnClickListener {
            (it.parent as SelectLayout).onClick(it)
            viewModel.ansD.observe(viewLifecycleOwner) {
                updateAnswer(it, AnswerTitle.D.title)
            }
        }
    }

    private fun updateAnswer(select: String, title: String) {
        if (quiz!!.correct.equals(select)) {
            isCorrect = true
        }
        onClickListener.updateAnswer(title, select, position, isCorrect)
    }

    fun set5050AnswerColor(position: Int) {
        cancelAnswer = position
        var textViewPosition = position
        var textView = TextView(requireContext())

        when (textViewPosition) {
            0 -> {
                textView = binding.tvAnswerA
            }
            1 -> {
                textView = binding.tvAnswerB
            }
            2 -> {
                textView = binding.tvAnswerC
            }
            3 -> {
                textView = binding.tvAnswerD
            }
        }
        textView.isClickable = false
        textView.setBackgroundResource(R.drawable.background_answer_5050)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_5050))
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {

        const val QUIZ_DATA_KEY = "QUIZ_DATA_KEY"
        const val POSITION_KEY = "POSITION_KEY"

        fun newInstance(
            quiz: Quiz,
            onClickListener: OnClickListener,
            position: Int
        ) =
            QuestionFragment().apply {
                arguments = bundleOf(
                    QUIZ_DATA_KEY to quiz,
                    POSITION_KEY to position
                )
                this.onClickListener = onClickListener
            }
    }

    interface OnClickListener {
        fun updateAnswer(
            titleAnswer: String,
            choosedAnswer: String,
            position: Int,
            isCorrect: Boolean
        )
    }
}
