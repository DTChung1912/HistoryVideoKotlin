package com.example.historyvideokotlin.fragments

import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserEditBinding
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.UserEditViewModel
import java.util.*

class UserEditFragment : BaseFragment<UserEditViewModel, FragmentUserEditBinding>() {

    private lateinit var editUser: User

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_edit
    }

    override fun getViewModel(): UserEditViewModel =
        ViewModelProvider(requireActivity()).get(UserEditViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        val user = arguments?.getSerializable(USER_KEY) as User
        setInfoUser(user)
        editUser = user
        binding.run {
            btnUpdate.setOnClickListener {
                updateUser(user)
                viewModel.userInfo.observe(this@UserEditFragment) { data ->
                    data.let {
                        setInfoUser(it)
                    }
                }
            }
        }
    }

    private fun setInfoUser(user: User) {
        binding.run {
            if (!user.user_image.isNullOrEmpty()) {
                Glide.with(requireContext()).load(user.user_image).into(ivUserAvatar)
            }
            edtUsername.setText(user.user_image)
            edtEmail.setText(user.email)
            edtAddress.setText(user.address)
            edtPhoneNumber.setText(user.phone_number)
            edtBirthday.setText(user.birthday)
        }
    }

    private fun updateUser(user: User) {
        binding.run {
            val name = edtUsername.text.trim().toString()
            val email = edtEmail.text.trim().toString()
            val birth = edtBirthday.text.trim().toString()
            val address = edtAddress.text.trim().toString()
            val phone = edtPhoneNumber.text.trim().toString()

            viewModel.updateUser(
                User(
                    user_id = user.user_id,
                    user_name = name,
                    user_image = user.user_image,
                    email = email,
                    birthday = birth,
                    phone_number = phone,
                    address = address,
                    last_active = user.last_active,
                    account_type_id = user.account_type_id
                )
            )
        }

    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(false)
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
        binding.run {
            ivBack.setOnClickListener {
                popFragment(HistoryUtils.getSlideTransitionAnimationOptions())
            }
        }
    }

    companion object {

        const val USER_KEY = "USER_KEY"

        @JvmStatic
        fun newInstance(user: User) =
            UserEditFragment().apply {
                arguments = bundleOf(USER_KEY to user)
            }
    }
}