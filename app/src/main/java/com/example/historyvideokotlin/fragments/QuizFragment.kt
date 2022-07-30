package com.example.historyvideokotlin.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.DetailActivity
import com.example.historyvideokotlin.adapters.QuizAdapter
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizBinding
import com.example.historyvideokotlin.model.PostTheme
import com.example.historyvideokotlin.utils.Constants.DETAIL_KEY
import com.example.historyvideokotlin.utils.Constants.QUIZ_DATA_KEY
import com.example.historyvideokotlin.utils.Constants.QUIZ_DETAIL_KEY
import com.example.historyvideokotlin.utils.Constants.RANDOM_QUIZ_ID_KEY
import com.example.historyvideokotlin.utils.Constants.RANDOM_QUIZ_NAME_KEY
import com.example.historyvideokotlin.viewmodels.QuizViewModel
import java.util.*

class QuizFragment : BaseFragment<QuizViewModel, FragmentQuizBinding>(),
    QuizAdapter.OnItemClickListener {

    private var adapter : QuizAdapter? = null
    private var theme : PostTheme? = null

    override fun getLayoutId(): Int = R.layout.fragment_quiz

    override fun getViewModel(): QuizViewModel =
        ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)


    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        viewModel.getThemeData()
        viewModel.themeList.observe(this, {data ->
            data.let {
                setRecyclerView(data)
            }
        })
        binding.itemQuizRandom.setOnClickListener {
            theme = PostTheme(RANDOM_QUIZ_ID_KEY, RANDOM_QUIZ_NAME_KEY)
            moveToQuizDetail(theme!!)
        }
    }

    private fun setRecyclerView(themeList: List<PostTheme>) {
        val linearLayoutManager = LinearLayoutManager(view?.context)
        adapter = QuizAdapter(themeList, requireContext(), this)
        binding.myRecyclerView.setHasFixedSize(true)
        binding.myRecyclerView.layoutManager = linearLayoutManager
        binding.myRecyclerView.adapter = adapter
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            QuizFragment()
    }

    override fun onItemClick(theme: PostTheme) {
        moveToQuizDetail(theme)
    }

    private fun moveToQuizDetail (theme: PostTheme) {
        val intent = Intent(requireActivity(),DetailActivity::class.java)
        intent.putExtra(DETAIL_KEY, QUIZ_DETAIL_KEY)
        intent.putExtra(QUIZ_DATA_KEY,theme.post_theme_id)
        requireActivity().startActivity(intent)
    }
}