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
import com.example.historyvideokotlin.viewmodels.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(),
    View.OnClickListener {

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun getLayoutId(): Int {
        return R.layout.fragment_register
    }

    override fun getViewModel(): RegisterViewModel =
        ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        setUpButtonClick()
    }

    override fun onClick(v: View?) {
        if (lifecycle.currentState !== Lifecycle.State.RESUMED) {
            return
        }
        when (v!!.id) {
            R.id.btnRegister -> {
                registerUser()
            }
            R.id.tvLogin -> {
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
                    viewModel.postUser(name, email)
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

    private fun setUpButtonClick() {
        binding.btnRegister.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RegisterFragment()
    }


}