package com.example.historyvideokotlin.fragments

import android.view.View.GONE
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.activities.MainActivity
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentMyPageBinding
import com.example.historyvideokotlin.repository.HistoryUserManager
import com.example.historyvideokotlin.viewmodels.MyPageViewModel
import java.util.*

class MyPageFragment : BaseFragment<MyPageViewModel, FragmentMyPageBinding>() {

    var userId = HistoryUserManager.instance.UserId()

    override fun getLayoutId(): Int = R.layout.fragment_my_page

    override fun getViewModel(): MyPageViewModel =
        ViewModelProvider(requireActivity()).get(MyPageViewModel::class.java)

    override fun initData() {
        binding.viewmodels = viewModel
        binding.run {
            toolbar.tvTitle.text = "Cá nhân"
            toolbar.ivSearch.visibility = GONE
            toolbar.ivMenu.setOnClickListener {
                (activity as? MainActivity)?.onSettingClick()
            }
        }
        setScreen()
    }

    fun setScreen() {
        if (isLogged()) {
            viewModel.getUserDetail()
            viewModel.userDetail.observe(this, { data ->
                data.let {
                    if (it.access_id == 1) {
                        replaceFragmentWithoutAnimation(
                            R.id.fclMyPage,
                            AdminFragment.newInstance(),
                            false,
                            null
                        )
                    } else {
                        replaceFragmentWithoutAnimation(
                            R.id.fclMyPage,
                            UserInfoFragment.newInstance(),
                            false,
                            null
                        )
                    }
                }
            })
        } else {
            replaceFragmentWithoutAnimation(
                R.id.fclMyPage,
                MyPageLoginFragment.newInstance(),
                false,
                null
            )
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomMenu()
        setScreen()
    }

    companion object {

        fun newInstance() = MyPageFragment()
    }
}
