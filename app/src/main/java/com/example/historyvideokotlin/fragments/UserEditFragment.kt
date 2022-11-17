package com.example.historyvideokotlin.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserEditBinding
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.Constants.REQUEST_CODE_USER_IMAGE
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.UserEditViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UserEditFragment : BaseFragment<UserEditViewModel, FragmentUserEditBinding>() {

    private lateinit var userInfo: User

    var storageReference: StorageReference? = null
    private var imageUri: Uri? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_edit
    }

    override fun getViewModel(): UserEditViewModel =
        ViewModelProvider(requireActivity()).get(UserEditViewModel::class.java)

    override fun initData() {
        binding.viewModel = viewModel
        storageReference = FirebaseStorage.getInstance().getReference("Avatar")
        userInfo = arguments?.getSerializable(USER_KEY) as User
        viewModel.setUser(userInfo)
        binding.btnUpdate.setOnClickListener {
            updateNewUser()
        }

        binding.ivUserAvatar.setOnClickListener {
            openImage()
        }

        binding.btnAdd.setOnClickListener {
            createUser()
        }

        binding.ivBack.setOnClickListener {
            back()
        }
    }

    private fun createUser() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("Avatar/" + fileName)

        if (!imageUri.toString().isNullOrEmpty()) {
            storage.putFile(imageUri!!)
                .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                        storage.downloadUrl.addOnSuccessListener {
                            MyLog.e("imageUrl", it.toString())

                            viewModel.register(
                                User(
                                    user_id = userInfo.user_id,
                                    user_name = binding.edtUsername.text.trim().toString(),
                                    user_image = userInfo.user_image,
                                    email = binding.edtEmail.text.trim().toString(),
                                    birthday = binding.edtBirthday.text.trim().toString(),
                                    phone_number = binding.edtPhoneNumber.text.trim().toString(),
                                    address = binding.edtAddress.text.trim().toString(),
                                    last_active = userInfo.last_active,
                                    account_type_id = userInfo.account_type_id,
                                    access_id = userInfo.access_id
                                )
                            )
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
            viewModel.register(
                User(
                    user_id = userInfo.user_id,
                    user_name = binding.edtUsername.text.trim().toString(),
                    user_image = userInfo.user_image,
                    email = binding.edtEmail.text.trim().toString(),
                    birthday = binding.edtBirthday.text.trim().toString(),
                    phone_number = binding.edtPhoneNumber.text.trim().toString(),
                    address = binding.edtAddress.text.trim().toString(),
                    last_active = userInfo.last_active,
                    account_type_id = userInfo.account_type_id,
                    access_id = userInfo.access_id
                )
            )
        }
    }

    private fun updateNewUser() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("Avatar/" + fileName)

        storage.putFile(imageUri!!)
            .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                    storage.downloadUrl.addOnSuccessListener {
                        MyLog.e("imageUrl", it.toString())

                        viewModel.updateUser(
                            User(
                                user_id = userInfo.user_id,
                                user_name = binding.edtUsername.text.trim().toString(),
                                user_image = userInfo.user_image,
                                email = binding.edtEmail.text.trim().toString(),
                                birthday = binding.edtBirthday.text.trim().toString(),
                                phone_number = binding.edtPhoneNumber.text.trim().toString(),
                                address = binding.edtAddress.text.trim().toString(),
                                last_active = userInfo.last_active,
                                account_type_id = userInfo.account_type_id,
                                access_id = userInfo.access_id
                            )
                        )
                    }
                    hideLoading()
                    showToast("Cập nhật thành công")
                }
            }).addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    hideLoading()
                    showToast("Cập nhật thất bại")
                }
            }).addOnProgressListener {
                showLoading()
            }
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_CODE_USER_IMAGE
        )
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = requireContext().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_USER_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Glide.with(requireContext()).load(imageUri).transform(CircleCrop())
                .into(binding.ivUserAvatar)
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
