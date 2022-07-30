package com.example.historyvideokotlin.fragments

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.LoginAndRegisterActivity
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentLoginBinding
import com.example.historyvideokotlin.viewmodels.LoginViewModel
import java.util.*


class HistoryLoginFragment : BaseFragment<LoginViewModel,FragmentLoginBinding>(), View.OnClickListener {
    var isUserLogined: Boolean = false

    override fun onStart() {
        super.onStart()

        if (isUserLogined) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun getViewModel(): LoginViewModel =
        ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {

        setUpButtonClick()
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
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
                (requireActivity() as LoginAndRegisterActivity ).loginWithFacebook()
            }
            R.id.ibZalo -> {
                (requireActivity() as LoginAndRegisterActivity ).loginWithZalo()
            }
            R.id.ibGoogle -> {
                (requireActivity() as LoginAndRegisterActivity ).loginWithGoogle()
            }
            R.id.tvForgotPassword -> {
                replaceFragment(R.id.fragmentContainer,ForgotPasswordFragment.newInstance(),true,null)
            }
            R.id.tvRegister -> {
                replaceFragment(R.id.fragmentContainer,RegisterFragment.newInstance(),true,null)
            }
        }
    }

    private fun setUpButtonClick() {
        binding.btnLogin.setOnClickListener(this)
        binding.ibFacebook.setOnClickListener(this)
        binding.ibZalo.setOnClickListener(this)
        binding.ibGoogle.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
    }

    private fun openMainActivity() {
        var email = binding.edtEmail.text.toString()
        var password = binding.edtPassword.text.toString()
        var isUserLogined = viewModel.loginWithEmail(email,password)

        if (isUserLogined) {
            Toast.makeText(
                requireContext(),
                "Failed to login, ",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HistoryLoginFragment()
    }
}