package com.example.historyvideokotlin.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentUserEditBinding
import com.example.historyvideokotlin.model.User
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.UserEditViewModel
import com.google.android.exoplayer2.C
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UserEditFragment : BaseFragment<UserEditViewModel, FragmentUserEditBinding>(){

    private lateinit var editUser: User

    var storageReference: StorageReference? = null
    private var imageUri: Uri? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_edit
    }

    override fun getViewModel(): UserEditViewModel =
        ViewModelProvider(requireActivity()).get(UserEditViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {
        storageReference = FirebaseStorage.getInstance().getReference("Avatar")
        val user = arguments?.getSerializable(USER_KEY) as User
        setInfoUser(user)
        editUser = user
        binding.run {
            btnUpdate.setOnClickListener {
                updateNewUser(user)
                viewModel.userInfo.observe(this@UserEditFragment) { data ->
                    data.let {
                        setInfoUser(it)
                    }
                }
            }
            ivUserAvatar.setOnClickListener {
                openImage()
            }
        }
    }

    private fun updateNewUser(user: User) {

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storage = FirebaseStorage.getInstance().getReference("Avatar/" + fileName)

        storage.putFile(imageUri!!)
            .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                    viewModel.loadingLiveData.postValue(false)
                    storage.downloadUrl.addOnSuccessListener {
                        MyLog.e("imageUrl",it.toString())
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
                    Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                }
            }).addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e: Exception) {
                    viewModel.loadingLiveData.postValue(false)
                    Toast.makeText(requireContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                }
            }).addOnProgressListener {
                viewModel.loadingLiveData.postValue(true)
            }

    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            123
        )
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = requireContext().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Glide.with(requireContext()).load(imageUri).transform(CircleCrop()).into(binding.ivUserAvatar)
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