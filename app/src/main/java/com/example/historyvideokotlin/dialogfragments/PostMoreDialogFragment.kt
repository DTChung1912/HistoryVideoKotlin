package com.example.historyvideokotlin.dialogfragments

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.historyvideokotlin.databinding.FragmentPostMoreDialogBinding

class PostMoreDialogFragment(val onItemClickListener: OnItemClickListener) : DialogFragment() {

    private lateinit var binding: FragmentPostMoreDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentPostMoreDialogBinding.inflate(LayoutInflater.from(context), container, false)

        val postId = arguments?.getInt(POST_ID_KEY)!!

        binding.run {
            tvLater.setOnClickListener {
                onItemClickListener.onLater(postId)
            }
            tvDownload.setOnClickListener {
                onItemClickListener.onDownload(postId)
            }
            tvShare.setOnClickListener {
                onItemClickListener.onShare(postId)
            }
            tvDontCare.setOnClickListener {
                onItemClickListener.onDontCare(postId)
            }
        }

        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
//        activity?.finish()
        this.dismiss()
//        activity?.overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
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
        const val POST_ID_KEY = "POST_ID_KEY"

        @JvmStatic
        fun newInstance(
            postId: Int,
            onItemClickListener: OnItemClickListener
        ) =
            PostMoreDialogFragment(onItemClickListener).apply {
                arguments = bundleOf(
                    POST_ID_KEY to postId
                )
            }
    }

    interface OnItemClickListener {
        fun onLater(postId: Int)
        fun onDownload(postId: Int)
        fun onShare(postId: Int)
        fun onDontCare(postId: Int)
    }
}