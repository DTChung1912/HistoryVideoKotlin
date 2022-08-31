package com.example.historyvideokotlin.fragments

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.AppEvent
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

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        binding.run {
            btnGenerate.setOnClickListener {
                if (TextUtils.isEmpty(edtPhoneNumber.text.toString())) {
                    Toast.makeText(requireContext(),"Bạn chưa nhập số điện thoại",Toast.LENGTH_SHORT).show()
                } else {
                    (activity as MainActivity).phoneSendVerifyCode(edtPhoneNumber.text.toString())
                    showLoading(true)
                }
            }
            btnVerify.setOnClickListener {
                if (TextUtils.isEmpty(edtPhoneNumber.text.toString())) {
                    Toast.makeText(requireContext(),"Bạn chưa nhập số điện thoại",Toast.LENGTH_SHORT).show()
                } else {
                    (activity as MainActivity).phoneSendVerifyCode(edtOTP.text.toString())
                }
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PhoneNumberLoginFragment()
    }
}
