package com.example.historyvideokotlin.dialogfragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.historyvideokotlin.adapters.QuizResultAdapter
import com.example.historyvideokotlin.databinding.FragmentQuizResultDialogBinding

class QuizResultDialogFragment(val onItemClickListener: ItemListener) : DialogFragment() {

    private lateinit var binding: FragmentQuizResultDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentQuizResultDialogBinding.inflate(LayoutInflater.from(context), container, false)

        val anwserList = arguments?.getStringArrayList(ANSWER_LIST_KEY) as List<String>
        binding.recyclerQuizResult.adapter = QuizResultAdapter(anwserList, requireContext())

        binding.btnContinue.setOnClickListener {
            onItemClickListener.onContinue()
            dismiss()
        }

        binding.ivBack.setOnClickListener {
            dismiss()
        }

        binding.btnComplete.setOnClickListener {
            onItemClickListener.onComplete()
            dismiss()
        }

        return binding.root
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {

        const val ANSWER_LIST_KEY = "ANSWER_LIST_KEY"

        @JvmStatic
        fun newInstance(anwserList: List<String>, onItemClickListener: ItemListener) =
            QuizResultDialogFragment(onItemClickListener).apply {
                arguments = bundleOf(
                    ANSWER_LIST_KEY to anwserList
                )
            }
    }

    interface ItemListener {
        fun onContinue()
        fun onComplete()
    }
}