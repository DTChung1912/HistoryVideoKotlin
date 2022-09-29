package com.example.historyvideokotlin.dialogfragments

import android.annotation.SuppressLint
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
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.databinding.FragmentDeleteMyVideoBottomSheetDialogBinding

class DeleteMyVideoBottomSheetDialogFragment(val onItemClickListener: OnItemClickListener) :
    DialogFragment() {

    private lateinit var binding: FragmentDeleteMyVideoBottomSheetDialogBinding

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteMyVideoBottomSheetDialogBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )

        val myVideoId = arguments?.getInt(MY_VIDEO_ID_KEY)
        val myVideoType = arguments?.getInt(MY_VIDEO_TYPE_KEY)

        binding.run {
            tvDelete.setOnClickListener {
                onItemClickListener.onDelete(myVideoId!!, myVideoType!!)
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
        const val MY_VIDEO_ID_KEY = "REPLY_KEY"
        const val MY_VIDEO_TYPE_KEY = "REPLY_KEY"

        @JvmStatic
        fun newInstance(
            myVideoId: Int,
            myVideoType: Int,
            onItemClickListener: OnItemClickListener
        ) =
            DeleteMyVideoBottomSheetDialogFragment(onItemClickListener).apply {
                arguments = bundleOf(
                    MY_VIDEO_ID_KEY to myVideoId,
                    MY_VIDEO_ID_KEY to myVideoType
                )
            }
    }

    interface OnItemClickListener {
        fun onDelete(myVideoId: Int, myVideoType: Int)
    }
}
