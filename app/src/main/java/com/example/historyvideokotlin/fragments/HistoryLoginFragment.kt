package com.example.historyvideokotlin.fragments

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentLoginBinding
import com.example.historyvideokotlin.viewmodels.LoginViewModel
import java.util.*

class HistoryLoginFragment :
    BaseFragment<LoginViewModel, FragmentLoginBinding>(),
    View.OnClickListener {
    var correctEmail = ""
    var correctPassword = ""

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModel(): LoginViewModel =
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

    override fun initData() {
        setUpButtonClick()
    }

    private fun rememberAccount(isRemember: Boolean) {
        if (isRemember) {
            binding.edtEmail.setText(correctEmail)
            binding.edtPassword.setText(correctPassword)
        } else {
            binding.edtEmail.setText("")
            binding.edtPassword.setText("")
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onClick(v: View?) {
        if (lifecycle.currentState !== Lifecycle.State.RESUMED) {
            return
        }
        when (v!!.id) {
            R.id.btnLogin -> {
                openMainActivity()
            }
            R.id.ibFacebook -> {
                (requireActivity() as MainActivity).loginWithFacebook()
            }
            R.id.ibPhoneNumber -> {
                (requireActivity() as MainActivity).loginWithPhoneNumber()
            }
            R.id.ibGoogle -> {
                (requireActivity() as MainActivity).loginWithGoogle()
            }
            R.id.tvForgotPassword -> {
                replaceFragment(
                    R.id.fragmentContainer,
                    ForgotPasswordFragment.newInstance(),
                    true,
                    null
                )
            }
            R.id.tvRegister -> {
                replaceFragment(R.id.fragmentContainer, RegisterFragment.newInstance(), true, null)
            }
        }
    }

    private fun setUpButtonClick() {
        binding.btnLogin.setOnClickListener(this)
        binding.ibFacebook.setOnClickListener(this)
        binding.ibPhoneNumber.setOnClickListener(this)
        binding.ibGoogle.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
    }

    private fun openMainActivity() {
        val email = binding.edtEmail.text.trim().toString()
        val password = binding.edtPassword.text.trim().toString()
        val isUserLogined = viewModel.loginWithEmail(email, password)

        if (isUserLogined) {
            correctEmail = binding.edtEmail.text.toString()
            correctPassword = binding.edtPassword.text.toString()
            if (binding.checkbox.isChecked) {
                rememberAccount(true)
            } else {
                rememberAccount(false)
            }
            back()
        } else {
            showToast("Đăng nhập thất bại. Vui lòng kiểm tra tài khoản hoặc mật khẩu")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HistoryLoginFragment()
    }
}
