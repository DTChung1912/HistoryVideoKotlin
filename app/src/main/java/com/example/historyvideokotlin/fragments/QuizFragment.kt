package com.example.historyvideokotlin.fragments

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.adapters.QuizAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizBinding
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.utils.Constants.RANDOM_QUIZ_ID_KEY
import com.example.historyvideokotlin.utils.Constants.RANDOM_QUIZ_NAME_KEY
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.QuizViewModel
import java.util.*

class QuizFragment : BaseFragment<QuizViewModel, FragmentQuizBinding>(),
    QuizAdapter.OnItemClickListener {

    private var adapter: QuizAdapter? = null
    private var theme: Theme? = null

    override fun getLayoutId(): Int = R.layout.fragment_quiz

    override fun getViewModel(): QuizViewModel =
        ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)


    

    override fun initData() {
        viewModel.getThemeData()
        viewModel.themeList.observe(this, { data ->
            data.let {
                setRecyclerView(data)
            }
        })
        binding.run {
            toolbar.tvTitle.text = "Kiểm tra"

            toolbar.ivSearch.visibility = View.GONE
            toolbar.ivMenu.setOnClickListener{
                (activity as? MainActivity)?.onSettingClick()
            }
            itemQuizRandom.setOnClickListener {
                theme = Theme(RANDOM_QUIZ_ID_KEY, RANDOM_QUIZ_NAME_KEY,"")
                moveToQuizDetail(theme!!)
            }
        }

    }

    private fun setRecyclerView(themeList: List<Theme>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = QuizAdapter(themeList, requireContext(), this)
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.layoutManager = linearLayoutManager
        binding.myRecyclerView.adapter = adapter
    }

    

    override fun onResume() {
        super.onResume()
        showBottomMenu()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            QuizFragment()
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
}