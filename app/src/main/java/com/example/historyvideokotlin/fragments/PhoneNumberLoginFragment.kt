package com.example.historyvideokotlin.fragments

import android.text.TextUtils
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPhoneNumberLoginBinding
import com.example.historyvideokotlin.viewmodels.PhoneNumberLoginViewModel
import java.util.*

class PhoneNumberLoginFragment :
    BaseFragment<PhoneNumberLoginViewModel, FragmentPhoneNumberLoginBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_phone_number_login
    }

    override fun getViewModel(): PhoneNumberLoginViewModel =
        ViewModelProvider(requireActivity()).get(PhoneNumberLoginViewModel::class.java)

    override fun initData() {
        binding.run {
            btnGenerate.setOnClickListener {
                if (TextUtils.isEmpty(edtPhoneNumber.text.toString())) {
                    showToast("Bạn chưa nhập số điện thoại")
                } else {
                    (activity as MainActivity).phoneSendVerifyCode(edtPhoneNumber.text.toString())
                    showLoading(true)
                }
            }
            btnVerify.setOnClickListener {
                if (TextUtils.isEmpty(edtPhoneNumber.text.toString())) {
                    showToast("Bạn chưa nhập số điện thoại")
                } else {
                    (activity as MainActivity).phoneSendVerifyCode(edtOTP.text.toString())
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PhoneNumberLoginFragment()
    }
}
