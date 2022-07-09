package com.example.historyvideokotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.viewmodels.QuizViewModel
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizBinding
import java.util.*

class QuizFragment : BaseFragment<QuizViewModel, FragmentQuizBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun getLayoutId(): Int = R.layout.fragment_quiz

    override fun getViewModel(): QuizViewModel =
        ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)


    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            QuizFragment()
    }
}