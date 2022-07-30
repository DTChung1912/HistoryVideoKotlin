package com.example.historyvideokotlin.fragments

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentForgotPasswordBinding
import com.example.historyvideokotlin.viewmodels.ForgotPasswordViewModel
import java.util.*

class ForgotPasswordFragment : BaseFragment<ForgotPasswordViewModel,FragmentForgotPasswordBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_forgot_password
    }

    override fun getViewModel(): ForgotPasswordViewModel =
        ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        var isReset = false
        binding.btnReset.setOnClickListener{
            isReset = viewModel.resetPassword(binding.edtEmail.text.toString())
            if (isReset) {
                Toast.makeText(requireContext(),"Xin kiểm tra lại Email của bạn", Toast.LENGTH_SHORT).show()
                replaceFragment(R.id.fragmentContainer,HistoryLoginFragment.newInstance(),true,null)
            } else {
                Toast.makeText(requireContext(),"fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForgotPasswordFragment()
    }
}