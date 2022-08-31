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
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.databinding.FragmentVideoMoreDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class VideoMoreDialogFragment( val onItemClickListener: OnItemClickListener) :
    BottomSheetDialogFragment() {

    private lateinit var binding: FragmentVideoMoreDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentVideoMoreDialogBinding.inflate(LayoutInflater.from(context), container, false)

        val videoId = arguments?.getString(VIDEO_ID_KEY).toString()

        binding.run {
            tvLater.setOnClickListener {
                onItemClickListener.onLater(videoId)
            }
            tvDownload.setOnClickListener {
                onItemClickListener.onDownload(videoId)
            }
            tvShare.setOnClickListener {
                onItemClickListener.onShare(videoId)
            }
            tvDontCare.setOnClickListener {
                onItemClickListener.onDontCare(videoId)
            }
        }

        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        activity?.finish()
        activity?.overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
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
        const val VIDEO_ID_KEY = "VIDEO_ID_KEY"

        @JvmStatic
        fun newInstance(videoId: String, onItemClickListener: OnItemClickListener) =
            VideoMoreDialogFragment(onItemClickListener).apply {
                arguments = bundleOf(
                    VIDEO_ID_KEY to videoId
                )
            }
    }

    interface OnItemClickListener {
        fun onLater(videoId: String)
        fun onDownload(videoId: String)
        fun onShare(videoId: String)
        fun onDontCare(videoId: String)
    }
}