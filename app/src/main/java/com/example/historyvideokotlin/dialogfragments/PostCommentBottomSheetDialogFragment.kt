package com.example.historyvideokotlin.dialogfragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.databinding.FragmentPostCommentDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostCommentBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var listener: PostCommentListener
    private lateinit var binding: FragmentPostCommentDialogBinding

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostCommentDialogBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )

        val url = arguments?.getString(KEY_URL)

        binding.url = url
        binding.edtComment.performClick()

        var content = ""
        binding.edtComment.doAfterTextChanged {
            content = it.toString()
        }

        binding.ivSend.setOnClickListener {
            if (content.isEmpty()) {
                Toast.makeText(requireContext(), "Bạn chưa nhập bình luận", Toast.LENGTH_SHORT)
                    .show()
            } else {
                listener.onPostComment(content)
                dismissAllowingStateLoss()
            }
        }

//        if (dialog!!.window != null) {
//            dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//        }
//        if (activity != null) {
//            val decorView = requireActivity().window.decorView
//            decorView.viewTreeObserver.addOnGlobalLayoutListener {
//                val displayFrame = Rect()
//                decorView.getWindowVisibleDisplayFrame(displayFrame)
//                val height = decorView.context.resources.displayMetrics.heightPixels
//                val heightDifference: Int = height - displayFrame.bottom
//                if (heightDifference != 0) {
//                    if (binding.item.getPaddingBottom() !== heightDifference) {
//                        binding.item.setPadding(0, 0, 0, heightDifference)
//                    }
//                } else {
//                    if (binding.item.getPaddingBottom() !== 0) {
//                        binding.item.setPadding(0, 0, 0, 0)
//                    }
//                }
//            }
//        }
//        dialog!!.setOnShowListener { dialog: DialogInterface ->
//            val d = dialog as BottomSheetDialog
//            val bottomSheetInternal =
//                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//                    ?: return@setOnShowListener
//            BottomSheetBehavior.from(bottomSheetInternal)
//                .setState(BottomSheetBehavior.STATE_EXPANDED)
//        }

        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        activity?.overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity?.window?.setDecorFitsSystemWindows(false)
        } else {
            // SOFT_INPUT_ADJUST_RESIZE was deprecated in API level 30
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtComment.performClick()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    companion object {

        const val KEY_URL = "KEY_URL"

        fun newInstance(userImage: String, listener: PostCommentListener) =
            PostCommentBottomSheetDialogFragment().apply {
                arguments = bundleOf(
                    KEY_URL to userImage
                )
                this.listener = listener
            }
    }
}

interface PostCommentListener {
    fun onPostComment(content: String)
}
