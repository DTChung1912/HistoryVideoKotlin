package com.example.historyvideokotlin.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.UserManagementAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserManagementBinding
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.UserManagementViewModel

class UserManagementFragment :
    BaseFragment<UserManagementViewModel, FragmentUserManagementBinding>() {
    override fun getLayoutId() = R.layout.fragment_user_management

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[UserManagementViewModel::class.java]

    override fun initData() {
        binding.toolbar.title = "Danh Sách Người Dùng"
        binding.viewModel = viewModel
        viewModel.getUserList()
        viewModel.userList.observe(viewLifecycleOwner) { data ->
            data.let {
                binding.recyclerView.adapter =
                    UserManagementAdapter(
                        it,
                        object : UserManagementAdapter.ItemListener {
                            override fun onEdit(user: User) {
                                pushFragment(
                                    UserEditFragment.newInstance(user),
                                    HistoryUtils.getSlideTransitionAnimationOptions()
                                )
                            }

                            override fun onDelete(userId: String) {
                                AlertDialog.Builder(requireContext())
                                    .setMessage("Bạn có chắc chắn muốn xóa")
                                    .setPositiveButton("Xóa") { dialog: DialogInterface, _: Int ->
                                        dialog.dismiss()
                                        viewModel.deleteUser(userId)
                                    }
                                    .setNegativeButton("Hủy") { dialog: DialogInterface, _: Int ->
                                        dialog.dismiss()
                                    }
                                    .create()
                                    .show()
                            }
                        }
                    )
            }
        }

        binding.btnAdd.setOnClickListener {
            pushFragment(
                UserEditFragment.newInstance(User()),
                HistoryUtils.getSlideTransitionAnimationOptions()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance() = UserManagementFragment()
    }
}
