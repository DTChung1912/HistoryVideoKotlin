package com.example.historyvideokotlin.dialogfragments

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.databinding.FragmentReplyBottomSheetDialogBinding
import com.example.historyvideokotlin.model.Reply

class ReplyBottomSheetDialogFragment(val onItemClickListener: OnItemClickListener) :
    DialogFragment() {

    private lateinit var binding: FragmentReplyBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReplyBottomSheetDialogBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )

        val userName = arguments?.getSerializable(USER_NAME_KEY).toString()

        binding.run {
            tvUserName.text = userName

//            if (!reply.user_image.isNullOrEmpty()) {
//                Glide.with(requireContext()).load(reply.user_image).into(civAvatar)
//            }

            var content = ""

            tvUserName.doAfterTextChanged {
                content = it.toString()
            }

            if (content.isEmpty()) {
                ivSendReply.visibility = GONE
            } else {
                ivSendReply.visibility = VISIBLE
                ivSendReply.setOnClickListener {
                    onItemClickListener.onSend(userName, content)
                }
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
        const val USER_NAME_KEY = "USER_NAME_KEY"

        @JvmStatic
        fun newInstance(userName: String, onItemClickListener: OnItemClickListener) =
            ReplyBottomSheetDialogFragment(onItemClickListener).apply {
                arguments = bundleOf(
                    USER_NAME_KEY to userName
                )
            }
    }

    interface OnItemClickListener {
        fun onSend(partnerName: String, content: String)
    }
}