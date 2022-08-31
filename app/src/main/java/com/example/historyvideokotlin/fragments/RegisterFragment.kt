package com.example.historyvideokotlin.fragments

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentRegisterBinding
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.RegisterViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(){

    val firebaseAuth = Firebase.auth

    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun getViewModel(): RegisterViewModel =
        ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        binding.run {
            ivUserAvatar.setOnClickListener {
                pushFragment(CameraFragment.newInstance(),HistoryUtils.getSlideTransitionAnimationOptions())
            }

            btnRegister.setOnClickListener {
                registerUser()
            }

            tvLogin.setOnClickListener {
                replaceFragment(
                    R.id.fragmentContainer,
                    HistoryLoginFragment.newInstance(),
                    true,
                    null
                )
            }
        }

    }

    private fun registerUser() {
        val name = binding.edtName.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result.user?.uid ?: "null"
                    viewModel.postUser(userId,name, email)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Đăng Ký Thất Bại", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RegisterFragment()
    }


}