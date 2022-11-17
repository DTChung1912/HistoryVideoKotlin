package com.example.historyvideokotlin.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.adapters.ThemeManagementAdapter
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentThemeManagementBinding
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.ThemeManagementViewModel

class ThemeManagementFragment :
    BaseFragment<ThemeManagementViewModel, FragmentThemeManagementBinding>() {
    override fun getLayoutId() = R.layout.fragment_theme_management

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[ThemeManagementViewModel::class.java]

    override fun initData() {
        binding.toolbar.title = "Danh Sách Chủ Đề"

        binding.viewModel = viewModel
        viewModel.getTheme()
        viewModel.themeList.observe(viewLifecycleOwner) { data ->
            data.let {
                binding.recyclerView.adapter =
                    ThemeManagementAdapter(
                        it,
                        object : ThemeManagementAdapter.ItemListener {
                            override fun onEdit(theme: Theme) {
                                pushFragment(
                                    ThemeEditFragment.newInstance(theme),
                                    HistoryUtils.getSlideTransitionAnimationOptions()
                                )
                            }

                            override fun onDelete(themeId: Int) {
                                AlertDialog.Builder(requireContext())
                                    .setMessage("Bạn có chắc chắn muốn xóa")
                                    .setPositiveButton("Xóa") { dialog: DialogInterface, _: Int ->
                                        dialog.dismiss()
                                        viewModel.deleteTheme(themeId)
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
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {
        fun newInstance() = ThemeManagementFragment()
    }
}
