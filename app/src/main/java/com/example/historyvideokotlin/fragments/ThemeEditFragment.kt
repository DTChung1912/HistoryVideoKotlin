package com.example.historyvideokotlin.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentThemeEditBinding
import com.example.historyvideokotlin.model.Theme
import com.example.historyvideokotlin.utils.Constants.REQUEST_CODE_THEME_IMAGE
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.ThemeEditViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ThemeEditFragment : BaseFragment<ThemeEditViewModel, FragmentThemeEditBinding>() {

    private var imageUri: Uri? = null
    private lateinit var theme: Theme

    override fun getLayoutId() = R.layout.fragment_theme_edit

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[ThemeEditViewModel::class.java]

    override fun initData() {
        binding.viewModel = viewModel
        theme = arguments?.getSerializable(KEY_THEME) as Theme
        viewModel.setTheme(theme)

        viewModel.isAddComplete.observe(viewLifecycleOwner) {
            if (it) {
                showToast("Thêm thành công")
            } else {
                showToast("Thêm thất bại")
            }
        }

        viewModel.isUpdateComplete.observe(viewLifecycleOwner) {
            if (it) {
                showToast("Cập nhật thành công")
            } else {
                showToast("Cập nhật thất bại")
            }
        }

        binding.ivImage.setOnClickListener {
            openImage()
        }

        binding.btnAdd.setOnClickListener {
            createTheme()
        }

        binding.btnUpdate.setOnClickListener {
            updateTheme()
        }
    }

    private fun createTheme() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storage =
            FirebaseStorage.getInstance().getReference("/HistoryImage/theme_image" + fileName)

        if (imageUri != null && imageUri.toString().isNotEmpty()) {
            storage.putFile(imageUri!!)
                .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                        storage.downloadUrl.addOnSuccessListener {
                            val theme = Theme(
                                theme_id = theme.theme_id,
                                theme_name = binding.edtTitle.text.trim().toString(),
                                theme_image = it.toString()
                            )
                            viewModel.createTheme(theme)
                            MyLog.e("imageUrl", it.toString())
                        }
                        hideLoading()
                        showToast("Thêm thành công")
                    }
                }).addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(e: Exception) {
                        hideLoading()
                        showToast("Thêm thất bại")
                    }
                }).addOnProgressListener {
                    showLoading()
                }
        } else {
            val themeCreate = Theme(
                theme_id = theme.theme_id,
                theme_name = binding.edtTitle.text.trim().toString(),
                theme_image = theme.theme_image
            )
            viewModel.createTheme(themeCreate)
        }
    }

    private fun updateTheme() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storage =
            FirebaseStorage.getInstance().getReference("/HistoryImage/theme_image" + fileName)

        if (imageUri != null && imageUri.toString().isNotEmpty()) {
            storage.putFile(imageUri!!)
                .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                        storage.downloadUrl.addOnSuccessListener {
                            val theme = Theme(
                                theme_id = theme.theme_id,
                                theme_name = binding.edtTitle.text.trim().toString(),
                                theme_image = it.toString()
                            )
                            viewModel.updateTheme(theme)
                            MyLog.e("imageUrl", it.toString())
                        }
                        hideLoading()
                        showToast("Thêm thành công")
                    }
                }).addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(e: Exception) {
                        hideLoading()
                        showToast("Thêm thất bại")
                    }
                }).addOnProgressListener {
                    showLoading()
                }
        } else {
            val themeUpdate = Theme(
                theme_id = theme.theme_id,
                theme_name = binding.edtTitle.text.trim().toString(),
                theme_image = theme.theme_image
            )
            viewModel.updateTheme(themeUpdate)
        }
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_CODE_THEME_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_THEME_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Glide.with(requireContext()).load(imageUri).into(binding.ivImage)
        }
    }

    companion object {
        const val KEY_THEME = "KEY_THEME"
        fun newInstance(theme: Theme) = ThemeEditFragment().apply {
            arguments = bundleOf(
                KEY_THEME to theme
            )
        }
    }
}
