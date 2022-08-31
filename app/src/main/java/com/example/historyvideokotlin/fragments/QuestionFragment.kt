package com.example.historyvideokotlin.fragments

import android.widget.TextView
import androidx.core.content.ContextCompat
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

class QuestionFragment(val onClickListener: OnClickListener) :
    BaseFragment<QuestionViewModel, FragmentQuestionBinding>() {

    private var quiz: Quiz? = null

    //    private var ramdomAnswerList = mutableListOf<String>()
    private var myClickListener: OnClickListener? = null
    private var position: Int = 0
    private var cancelAnswer = -1

    override fun getLayoutId(): Int {
        return R.layout.fragment_question
    }

    override fun getViewModel(): QuestionViewModel =
        ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        quiz = arguments?.getSerializable(QUIZ_DATA_KEY) as Quiz
        val ramdomAnswerList = arguments?.getStringArrayList(RANDOM_ANSWER_LIST_KEY) as List<String>
        myClickListener = onClickListener
        position = arguments?.getInt(POSITION_KEY)!!

        myClickListener!!.updatePosition(position)

        binding.run {
            val number: Int = position + 1
            tvNumber.text = "Câu " + number + ":"
            if (!quiz!!.image.isNullOrEmpty()) {
                Glide.with(requireContext()).load(quiz!!.image).into(ivImageQuiz)
            }
            tvQuiz.text = quiz!!.question
            tvAnswerA.text = "A: " + ramdomAnswerList[0]
            tvAnswerB.text = "B: " + ramdomAnswerList[1]
            tvAnswerC.text = "C: " + ramdomAnswerList[2]
            tvAnswerD.text = "D: " + ramdomAnswerList[3]

            tvAnswerA.setOnClickListener {
                setAnswerColor(it as TextView)
                myClickListener!!.updateAnswer("A", ramdomAnswerList[0], position)
            }

            tvAnswerB.setOnClickListener {
                setAnswerColor(it as TextView)
                myClickListener!!.updateAnswer("B", ramdomAnswerList[1], position)
            }

            tvAnswerC.setOnClickListener {
                setAnswerColor(it as TextView)

                myClickListener!!.updateAnswer("C", ramdomAnswerList[2], position)
            }

            tvAnswerD.setOnClickListener {
                setAnswerColor(it as TextView)
                myClickListener!!.updateAnswer("D", ramdomAnswerList[3], position)
            }


        }
    }

    private fun setChoosedAnswer(textView: TextView) {
        textView.setBackgroundResource(R.drawable.background_answer_choosed)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    private fun setAnswerColorDefault(textView: TextView) {
        textView.setBackgroundResource(R.drawable.background_answer)
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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

    private fun setAnswerClick(isClick: Boolean) {
        if (isClick) {
            binding.run {
                tvAnswerA.isClickable = true
                tvAnswerB.isClickable = true
                tvAnswerC.isClickable = true
                tvAnswerD.isClickable = true
            }
        } else {
            binding.run {
                tvAnswerA.isClickable = false
                tvAnswerB.isClickable = false
                tvAnswerC.isClickable = false
                tvAnswerD.isClickable = false
            }
        }
    }

    private fun setAnswerColor(textView: TextView) {
        val anwserList = arrayListOf<TextView>(
            binding.tvAnswerA,
            binding.tvAnswerB,
            binding.tvAnswerC,
            binding.tvAnswerD
        )

        for (i in 0 until anwserList.size) {
            if (cancelAnswer.equals(i)) {
                anwserList.removeAt(i)
            }
        }

        for (i in 0 until anwserList.size) {
            if (anwserList[i].equals(textView)) {
                setChoosedAnswer(textView)
            } else {
                setAnswerColorDefault(anwserList[i])
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    companion object {

        const val QUIZ_DATA_KEY = "QUIZ_DATA_KEY"
        const val RANDOM_ANSWER_LIST_KEY = "RANDOM_ANSWER_LIST_KEY"
        const val POSITION_KEY = "POSITION_KEY"

        @JvmStatic
        fun newInstance(
            quiz: Quiz,
            ramdomAnswerList: MutableList<String>,
            onClickListener: OnClickListener,
            position: Int
        ) =
            QuestionFragment(onClickListener).apply {
                arguments = bundleOf(
                    QUIZ_DATA_KEY to quiz,
                    RANDOM_ANSWER_LIST_KEY to ramdomAnswerList,
                    POSITION_KEY to position
                )
            }
    }

    interface OnClickListener {
        fun updateAnswer(titleAnswer: String, choosedAnswer: String, position: Int)

        fun updatePosition(position: Int)
    }
}