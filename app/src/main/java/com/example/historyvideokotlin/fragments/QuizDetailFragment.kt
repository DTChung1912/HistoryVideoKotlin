package com.example.historyvideokotlin.fragments

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.QuizDetailAdapter
import com.example.historyvideokotlin.adapters.VideoAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizDetailBinding
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.viewmodels.QuizDetailViewModel
import com.example.historyvideokotlin.viewmodels.QuizViewModel
import java.util.*

class QuizDetailFragment : BaseFragment<QuizDetailViewModel, FragmentQuizDetailBinding>() {

    private var theme_id = 1
    private var adapter: QuizDetailAdapter? = null

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
        viewModel.quizList.observe(this, { data ->
            data.let {
//                setRecyclerView(data)
//                adapter = QuizDetailAdapter(data, requireContext())
//                binding.viewPager.adapter = adapter
//                setUpViewPager(binding.viewPager,data)
                binding.viewPager.adapter = PagerAdapter(this,data)
            }
        })

        binding.ivBack.setOnClickListener {
            requireActivity().finish()
        }
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

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {
        const val THEME_KEY = "THEME_KEY"

        @JvmStatic
        fun newInstance(theme_id: Int) =
            QuizDetailFragment().apply {
                arguments = bundleOf(THEME_KEY to theme_id)
            }
    }

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

    private class PagerAdapter(fragment: Fragment, val quizList: List<Quiz>) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = quizList.size

        override fun createFragment(position: Int): Fragment {

            val quiz = quizList[position]
            val answerList = listOf(quiz.correct, quiz.incorrect_1, quiz.incorrect_2, quiz.incorrect_3 )
            val ramdomAnswerList : MutableList<String> = emptyList<String>().toMutableList()
            while (ramdomAnswerList.size < 4) {
                val i = answerList.random()
                if (!ramdomAnswerList.contains(i)) {
                    ramdomAnswerList.add(i)
                }
            }
            return QuestionFragment.newInstance(quizList[position], ramdomAnswerList)
        }
    }
}