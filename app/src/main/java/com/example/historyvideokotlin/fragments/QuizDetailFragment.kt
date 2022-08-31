package com.example.historyvideokotlin.fragments

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.CountDownTimer
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizDetailBinding
import com.example.historyvideokotlin.dialogfragments.QuizResultDialogFragment
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.QuizResult
import com.example.historyvideokotlin.ui.FragmentNavigation
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.QuizDetailViewModel
import com.ncapdevi.fragnav.FragNavTransactionOptions
import java.util.*

class QuizDetailFragment : BaseFragment<QuizDetailViewModel, FragmentQuizDetailBinding>(),
    QuestionFragment.OnClickListener, QuizResultDialogFragment.OnItemClickListener,
    ResultStatisticFragment.OnItemClickListener {

    private var theme_id = 1
    private var currentFragmentPosition = 0
    private lateinit var quizResutl : QuizResult

    private var titleAnswerList = mutableListOf<String>()
    private var choosedAnswerList = mutableListOf<String>()
    private var correctAnswerList = mutableListOf<String>()

    private lateinit var fragmentNavigation: FragmentNavigation

    private lateinit var countDownTimer: CountDownTimer
    val start = 60_000L
    var timer = start
    private lateinit var pagerAdapter: PagerAdapter

    private var quizList = mutableListOf<Quiz>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_quiz_detail
    }

    override fun getViewModel(): QuizDetailViewModel =
        ViewModelProvider(requireActivity()).get(QuizDetailViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null


    override fun initData() {
        theme_id = arguments?.getInt(THEME_KEY)!!

        viewModel.getQuizData()
//        viewModel.getQuiz(theme_id)
        setTextTimer()
        startTimer()
        viewModel.quizList.observe(this, { data ->
            data.let {
//                setRecyclerView(data)
//                adapter = QuizDetailAdapter(data, requireContext())
//                binding.viewPager.adapter = adapter
//                setUpViewPager(binding.viewPager,data)
                quizList.addAll(it)

                titleAnswerList.clear()
                choosedAnswerList.clear()
                correctAnswerList.clear()

                for (i in 0 until it.size) {

                    titleAnswerList.add("")
                    choosedAnswerList.add("")
                    correctAnswerList.add(it[i].correct)

                }
                pagerAdapter = PagerAdapter(this, data, this)
                binding.viewPager.adapter = pagerAdapter

            }
        })

        binding.ivBack.setOnClickListener {

            popFragment(HistoryUtils.getSlideTransitionAnimationOptions())
//            requireActivity().finish()
        }

        binding.btn5050.setOnClickListener {
            (pagerAdapter.getCurrentFragment(currentFragmentPosition) as QuestionFragment).set5050AnswerColor(
                0
            )
        }

        binding.btnComplete.setOnClickListener {
            QuizResultDialogFragment.newInstance(titleAnswerList, this)
                .show(parentFragmentManager, null)
        }

    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (context is FragmentNavigation) {
            fragmentNavigation = context as FragmentNavigation
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
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

    private fun setUpViewPager(viewPager: ViewPager2, quizList: List<Quiz>) {
        val centerItem = Integer.MAX_VALUE / 2
        viewPager.apply {
            setCurrentItem(centerItem - centerItem % quizList.size, false)
            //if item is set up to display, we don't need set up again
            if (viewPager.itemDecorationCount > 0) {
                return
            }
            viewPager.offscreenPageLimit = 1
            //width of next and previous item will visible
            val widthOtherVisible = resources.getDimension(R.dimen.ndp_8)
            val currentItemHorizontalMargin = resources.getDimension(R.dimen.ndp_16)
            val pageTranslationX = widthOtherVisible + currentItemHorizontalMargin
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
            }
            setPageTransformer(pageTransformer)
            addItemDecoration(HorizontalMarginItemDecoration(context, R.dimen.ndp_16))
        }
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
            if (currentItem == 0) {
                currentItem++
                return
            }
            currentItem--
        }
    }

//    private fun setRecyclerView(quizList: List<Quiz>) {
//        val linearLayoutManager = LinearLayoutManager(view?.context, LinearLayoutManager.HORIZONTAL, false)
//        adapter = QuizDetailAdapter(quizList, requireContext())
//        binding.myRecyclerView.setHasFixedSize(true)
//        binding.myRecyclerView.layoutManager = linearLayoutManager
//        binding.myRecyclerView.adapter = adapter
//    }

    class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
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

            val quiz = quizList[position]
            val answerList =
                listOf(quiz.correct, quiz.incorrect_1, quiz.incorrect_2, quiz.incorrect_3)
            val ramdomAnswerList = mutableListOf<String>()
            while (ramdomAnswerList.size < 4) {
                val i = answerList.random()
                if (!ramdomAnswerList.contains(i)) {
                    ramdomAnswerList.add(i)
                }
            }

            val fragment = QuestionFragment.newInstance(
                quizList[position],
                ramdomAnswerList,
                onClickListener,
                position
            )
            mapFragments!!.put(position, fragment)

            return fragment
        }
    }

    override fun updateAnswer(titleAnswer: String, choosedAnswer: String, position: Int) {
        titleAnswerList.set(position, titleAnswer)
        choosedAnswerList.set(position, choosedAnswer)
    }

    override fun updatePosition(position: Int) {
        currentFragmentPosition = position
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {
        const val THEME_KEY = "THEME_KEY"

        @JvmStatic
        fun newInstance(theme_id: Int) =
            QuizDetailFragment().apply {
                arguments = bundleOf(
                    THEME_KEY to theme_id,
                )
            }
    }

    override fun onReturn() {
        popFragment(HistoryUtils.getSlideTransitionAnimationOptions())
        titleAnswerList.clear()
        choosedAnswerList.clear()
    }

    override fun onWatchAnswer() {
        pushFragment(
            WatchAnswerFragment.newInstance(quizList, choosedAnswerList),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }


    override fun onBack() {
        titleAnswerList.clear()
        choosedAnswerList.clear()
        popFragments(2, HistoryUtils.getSlideTransitionAnimationOptions())
    }

    override fun onContinue() {
        titleAnswerList.clear()
        choosedAnswerList.clear()
    }

    override fun onComplete() {
        pushFragment(
            ResultStatisticFragment.newInstance(
                choosedAnswerList,
                correctAnswerList,
                this
            ), FragNavTransactionOptions.Builder().allowReordering(true).build()
        )
    }
}