package com.example.historyvideokotlin.fragments

import android.os.CountDownTimer
import android.view.Gravity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.AnswerListAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizDetailBinding
import com.example.historyvideokotlin.dialogfragments.QuizCloseDialogFragment
import com.example.historyvideokotlin.dialogfragments.QuizResultDialogFragment
import com.example.historyvideokotlin.model.*
import com.example.historyvideokotlin.viewmodels.QuizDetailViewModel
import com.ncapdevi.fragnav.FragNavTransactionOptions

class QuizDetailFragment :
    BaseFragment<QuizDetailViewModel, FragmentQuizDetailBinding>(),
    QuestionFragment.OnClickListener,
    AnswerListAdapter.ItemClickListener,
    QuizResultDialogFragment.ItemListener {

    private var theme_id = 1
    private var currentFragmentPosition = 0
    private lateinit var quizResutl: QuizResult

    private lateinit var countDownTimer: CountDownTimer
    val start = 60_000L
    var timer = start
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var answerAdapter: AnswerListAdapter
    private var answerList = mutableListOf<AnswerModel>()

    private var quizList = mutableListOf<Quiz>()
    private var selectList = mutableListOf<SelectAnswer>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_quiz_detail
    }

    override fun getViewModel(): QuizDetailViewModel =
        ViewModelProvider(requireActivity()).get(QuizDetailViewModel::class.java)

    override fun initData() {
        theme_id = arguments?.getInt(THEME_KEY)!!

        viewModel.getQuizData()
//        viewModel.getQuiz(theme_id)
        setTextTimer()
        startTimer()
        viewModel.quizList.observe(this, { data ->
            data.let {
                quizList.clear()
                quizList.addAll(it)
                viewModel.setCorrectList(quizList)
                pagerAdapter = PagerAdapter(this, quizList, this)
                binding.viewPager.adapter = pagerAdapter
            }
        })

        binding.ivBack.setOnClickListener {
            QuizCloseDialogFragment.newInstance(object :
                    QuizCloseDialogFragment.OnItemClickListener {
                    override fun onClose() {
                        back()
                    }
                }).show(parentFragmentManager, null)
        }

        binding.btn5050.setOnClickListener {
//            (pagerAdapter.getCurrentFragment(currentFragmentPosition) as QuestionFragment).set5050AnswerColor(
//                0
//            )
        }

        binding.btnComplete.setOnClickListener {
            viewModel.titleAnswerList.observe(viewLifecycleOwner) {
                QuizResultDialogFragment.newInstance(it, this).show(parentFragmentManager, null)
            }

            countDownTimer.cancel()
        }

        binding.ivMenu.setOnClickListener {
            openDrawer()
        }
        binding.btnNext.setOnClickListener {
            goToNext()
        }
        binding.btnPrev.setOnClickListener {
            goToPrevious()
        }
    }

    private fun openDrawer() {
        binding.drawer.openDrawer(Gravity.RIGHT)
        viewModel.selecAnswerList.observe(viewLifecycleOwner) {
            answerAdapter = AnswerListAdapter(it, this)
            binding.fragmentAnswer.recyclerAnswer.adapter = answerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    private fun setTextTimer() {
        var m = (timer / 1000) / 60
        var s = (timer / 1000) % 60
        var format = String.format("%02d:%02d", m, s)

        binding.tvQuizTime.setText(format)
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(timer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished
                setTextTimer()
            }

            override fun onFinish() {
                binding.tvQuizTime.text = "Hết giờ!"
//                pushFragment(
//                    ResultStatisticFragment.newInstance(
//                        percentCorrect,
//                        percentIncorrect,
//                        percentNotAnwsered
//                    ), FragNavTransactionOptions.Builder().allowReordering(true).build()
//                )
            }
        }.start()
    }

    private fun goToNext() {
        binding.viewPager.apply {
            adapter?.let { it ->
                if (currentItem < it.itemCount - 1) {
                    currentItem++
                    return
                }
                currentItem = 0
            }
        }
    }

    private fun goToPrevious() {
        binding.viewPager.apply {
//            if (currentItem == 0) {
//                currentItem++
//                return
//            }
            if (currentItem < 1) {
//                currentItem++
                return
            }
            currentItem--
        }
    }

    private class PagerAdapter(
        fragment: Fragment,
        val quizList: List<Quiz>,
        val onClickListener: QuestionFragment.OnClickListener
    ) :
        FragmentStateAdapter(fragment) {
        private var mapFragments: HashMap<Int, Fragment>? = null

        fun getCurrentFragment(position: Int): Fragment? {
            return mapFragments!!.get(position)
        }

        override fun getItemCount(): Int = quizList.size

        override fun createFragment(position: Int): Fragment {
            mapFragments = HashMap()

            val fragment = QuestionFragment.newInstance(
                quizList[position],
                onClickListener,
                position
            )
            mapFragments!!.put(position, fragment)

            return fragment
        }
    }

    override fun updateAnswer(
        titleAnswer: String,
        choosedAnswer: String,
        position: Int,
        isCorrect: Boolean
    ) {
        val selectAnswer = SelectAnswer(position + 1, titleAnswer, choosedAnswer)
        viewModel.setSelectAnswer(selectAnswer)
        viewModel.checkAnswer(isCorrect, position)
        viewModel.setTitleAnswer(titleAnswer, position)
        if (!isCorrect) {
            viewModel.setAnswerModelList(titleAnswer, position)
        }
    }

    override fun onClick(position: Int) {
        binding.drawer.closeDrawer(Gravity.RIGHT)
        binding.viewPager.currentItem = position
    }

    override fun onContinue() {
        startTimer()
    }

    override fun onComplete() {
        var resultCount = ResultCount(0, 0, 0)
        viewModel.setResult()
        viewModel.result.observe(viewLifecycleOwner) {
            resultCount = it
        }
        viewModel.selecAnswerList.observe(viewLifecycleOwner) {
            selectList.clear()
            selectList.addAll(it)
        }
        viewModel.answerModelList.observe(viewLifecycleOwner) {
            answerList.clear()
            answerList.addAll(it)
        }
        pushFragment(
            ResultStatisticFragment.newInstance(resultCount, quizList, selectList, answerList),
            FragNavTransactionOptions.Builder().allowReordering(true).build()
        )
    }

    companion object {
        const val THEME_KEY = "THEME_KEY"

        fun newInstance(theme_id: Int) =
            QuizDetailFragment().apply {
                arguments = bundleOf(
                    THEME_KEY to theme_id
                )
            }
    }
}
