package com.example.historyvideokotlin.dialogfragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import com.example.historyvideokotlin.databinding.FragmentQuizCloseDialogBinding

class QuizCloseDialogFragment() : DialogFragment() {

    private lateinit var binding: FragmentQuizCloseDialogBinding
    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            FragmentQuizCloseDialogBinding.inflate(LayoutInflater.from(context), container, false)

        binding.btnClose.setOnClickListener {
            onItemClickListener.onClose()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.ivClose.setOnClickListener {
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
        @JvmStatic
        fun newInstance(onItemClickListener: OnItemClickListener) =
            QuizCloseDialogFragment().apply {
                this.onItemClickListener = onItemClickListener
            }
    }

    interface OnItemClickListener {
        fun onClose()
    }
}