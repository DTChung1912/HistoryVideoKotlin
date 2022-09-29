package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPageBinding
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.MyPageViewModel
import java.util.*

class MyPageFragment : BaseFragment<MyPageViewModel, FragmentMyPageBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_my_page

    override fun getViewModel(): MyPageViewModel =
        ViewModelProvider(requireActivity()).get(MyPageViewModel::class.java)

    

    override fun initData() {
        binding.viewmodels = viewModel

        viewModel.getUser()
        val userList = mutableListOf<User>()
        viewModel.userList.observe(this, { data ->
            data.let {
                userList.addAll(it)
            }
        })
        setScreen()

        viewModel.refreshUser()

        binding.run {
            toolbar.tvTitle.text = "Cá nhân"
            toolbar.ivSearch.visibility = GONE
            toolbar.ivMenu.setOnClickListener {
                (activity as? MainActivity)?.onSettingClick()
            }
        }
    }

    private fun setScreen() {
        showLoading()
        if (HistoryUserManager.instance.checkUserLogined()) {
            replaceFragment(R.id.fclMyPage, UserInfoFragment.newInstance(), false, null)
            hideLoading()
        } else {
            replaceFragment(R.id.fclMyPage, MyPageLoginFragment.newInstance(), false, null)
            hideLoading()
        }
    }

    

    override fun onResume() {
        super.onResume()
        showBottomMenu()
        setScreen()
    }

    companion object {

        @JvmStatic
        fun newInstance() = MyPageFragment()
    }
}
