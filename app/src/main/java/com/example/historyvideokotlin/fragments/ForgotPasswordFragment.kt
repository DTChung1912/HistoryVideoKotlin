package com.example.historyvideokotlin.fragments

import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentForgotPasswordBinding
import com.example.historyvideokotlin.viewmodels.ForgotPasswordViewModel
import java.util.*

class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordViewModel, FragmentForgotPasswordBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_forgot_password
    }

    override fun getViewModel(): ForgotPasswordViewModel =
        ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel::class.java)

    override fun initData() {
        var isReset = false
        binding.btnReset.setOnClickListener {
            isReset = viewModel.resetPassword(binding.edtEmail.text.toString())
            if (isReset) {
                showToast("Xin kiểm tra lại Email của bạn")
                replaceFragment(
                    R.id.fragmentContainer,
                    HistoryLoginFragment.newInstance(),
                    true,
                    null
                )
            } else {
                showToast("fail")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance() = ForgotPasswordFragment()
    }
}
