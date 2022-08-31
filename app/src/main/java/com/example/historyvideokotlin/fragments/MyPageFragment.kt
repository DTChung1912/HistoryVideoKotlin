package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPageBinding
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.viewmodels.MyPageViewModel
import java.util.*

class MyPageFragment : BaseFragment<MyPageViewModel, FragmentMyPageBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_my_page

    override fun getViewModel(): MyPageViewModel =
        ViewModelProvider(requireActivity()).get(MyPageViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        binding.viewmodels = viewModel

        viewModel.getUser()
        val userList = mutableListOf<User>()
        viewModel.userList.observe(this, { data ->
            data.let {
                userList.addAll(it)
            }
        })
        if (HistoryUserManager.checkUserLogined()) {
            replaceFragment(R.id.fclMyPage, UserInfoFragment.newInstance(), false, null)
        } else {
            replaceFragment(R.id.fclMyPage, MyPageLoginFragment.newInstance(), false, null)
        }

        viewModel.refreshUser()

        binding.run {
            toolbar.tvTitle.text = "Cá nhân"
//            toolbar.ivSearch.setOnClickListener{
//                (activity as? MainActivity)?.onSearchClick()
//            }
            toolbar.ivSearch.visibility = GONE
            toolbar.ivMenu.setOnClickListener {
                (activity as? MainActivity)?.onSettingClick()
            }
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {

    }

    override fun onResume() {
        super.onResume()
        showBottomMenu(true)
    }

    companion object {

        @JvmStatic
        fun newInstance() = MyPageFragment()
    }

}