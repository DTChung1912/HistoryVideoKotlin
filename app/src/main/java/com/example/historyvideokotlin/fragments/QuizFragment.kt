package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.adapters.QuizAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizBinding
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.utils.Constants.RANDOM_QUIZ_ID_KEY
import com.example.historyvideokotlin.utils.Constants.RANDOM_QUIZ_NAME_KEY
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.QuizViewModel

class QuizFragment :
    BaseFragment<QuizViewModel, FragmentQuizBinding>(),
    QuizAdapter.OnItemClickListener {

    private var adapter: QuizAdapter? = null
    private var theme: Theme? = null

    override fun getLayoutId(): Int = R.layout.fragment_quiz

    override fun getViewModel(): QuizViewModel =
        ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        viewModel.getThemeData()
        viewModel.themeList.observe(this, { data ->
            data.let {
                binding.myRecyclerView.adapter = QuizAdapter(it, requireContext(), this)
            }
        })
        binding.toolbar.run {
            tvTitle.text = "Kiểm tra"
            ivSearch.visibility = GONE
            ivMenu.setOnClickListener {
                (activity as? MainActivity)?.onSettingClick()
            }
        }

        binding.itemQuizRandom.setOnClickListener {
            theme = Theme(RANDOM_QUIZ_ID_KEY, RANDOM_QUIZ_NAME_KEY, "")
            moveToQuizDetail(theme!!)
        }

        binding.tvRefresh.setOnClickListener {
            viewModel.getThemeData()
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu()
    }

    override fun onItemClick(theme: Theme) {
        moveToQuizDetail(theme)
    }

    private fun moveToQuizDetail(theme: Theme) {
        pushFragment(
            QuizDetailFragment.newInstance(theme.theme_id),
            HistoryUtils.getSlideTransitionAnimationOptions()
        )
    }

    companion object {
        fun newInstance() = QuizFragment()
    }
}
