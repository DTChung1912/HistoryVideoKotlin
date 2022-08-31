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
import com.example.historyvideokotlin.databinding.FragmentLoginDialogBinding

class LoginDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentLoginDialogBinding
    private var listener : OnLoginItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginDialogBinding.inflate(LayoutInflater.from(context), container, false)

        binding.run {
            btnLogin.setOnClickListener {
                dismiss()
                listener?.onLogin()
            }

            btnRegister.setOnClickListener {
                dismiss()
                listener?.onRegister()
            }

            tvCancel.setOnClickListener {
                dismiss()
            }
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
        fun newInstance(onItemClickListener: OnLoginItemClickListener?) =
            LoginDialogFragment().apply {
                listener = onItemClickListener
            }
    }
}

interface OnLoginItemClickListener {
    fun onLogin()
    fun onRegister()
}