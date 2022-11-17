package com.example.historyvideokotlin.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.QuizManagementAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizManagementBinding
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.QuizManagementViewModel

class QuizManagementFragment :
    BaseFragment<QuizManagementViewModel, FragmentQuizManagementBinding>() {
    override fun getLayoutId() = R.layout.fragment_quiz_management

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[QuizManagementViewModel::class.java]

    override fun initData() {
        binding.toolbar.title = "Danh Sách Câu Hỏi"
        binding.viewModel = viewModel
        viewModel.getQuizList()
        viewModel.quizList.observe(viewLifecycleOwner) { data ->
            data.let {
                binding.recyclerView.adapter =
                    QuizManagementAdapter(
                        it,
                        object : QuizManagementAdapter.ItemListener {
                            override fun onEdit(quiz: Quiz) {
                                pushFragment(
                                    QuizEditFragment.newInstance(quiz),
                                    HistoryUtils.getSlideTransitionAnimationOptions()
                                )
                            }

                            override fun onDelete(quizId: Int) {
                                AlertDialog.Builder(requireContext())
                                    .setMessage("Bạn có chắc chắn muốn xóa")
                                    .setPositiveButton("Xóa") { dialog: DialogInterface, _: Int ->
                                        dialog.dismiss()
                                        viewModel.deleteQuiz(quizId)
                                    }
                                    .setNegativeButton("Hủy") { dialog: DialogInterface, _: Int ->
                                        dialog.dismiss()
                                    }
                                    .create()
                                    .show()
                            }
                        }
                    )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance() = QuizManagementFragment()
    }
}
