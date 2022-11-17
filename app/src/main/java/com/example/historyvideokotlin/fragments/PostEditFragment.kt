package com.example.historyvideokotlin.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostEditBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.model.PostRequest
import com.example.historyvideokotlin.utils.Constants
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.PostEditViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class PostEditFragment : BaseFragment<PostEditViewModel, FragmentPostEditBinding>() {

    private var imageUri: Uri? = null
    private lateinit var post: Post

    override fun getLayoutId() = R.layout.fragment_post_edit

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[PostEditViewModel::class.java]

    override fun initData() {
        binding.viewModel = viewModel
        post = arguments?.getSerializable(KEY_POST) as Post
        binding.toolbar.title = if (post.post_id == 0) "Thêm mới bài viết" else "Cập nhật bài viết"
        viewModel.setPost(post)

        viewModel.postTypeList.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(requireContext(), R.layout.list_option, it)
            binding.spinnerPostType.setAdapter(adapter)
        }

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

        binding.btnImage.setOnClickListener {
            openImage()
        }

        binding.btnAdd.setOnClickListener {
            createPost()
        }

        binding.btnUpdate.setOnClickListener {
            updatePost()
        }
    }

    private fun createPost() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storage =
            FirebaseStorage.getInstance().getReference("/HistoryImage/post_image/" + fileName)

        if (imageUri != null && imageUri.toString().isNotEmpty()) {
            storage.putFile(imageUri!!)
                .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                        storage.downloadUrl.addOnSuccessListener {
                            val postRequest = PostRequest(
                                post_id = post.post_id,
                                post_type_id = binding.spinnerPostType.selectedItemPosition + 1,
                                theme_id = 1,
                                title = binding.edtTitle.text.trim().toString(),
                                content = post.content,
                                pdf = post.pdf,
                                image = it.toString(),
                                description = binding.edtDescription.text.trim().toString(),
                                timeline = binding.edtTimeline.text.trim().toString(),
                                place = post.place,
                                read_count = post.read_count,
                                download_count = post.download_count,
                                rate_count = post.rate_count,
                                date_submitted = post.date_submitted
                            )
                            viewModel.createPost(postRequest)
                            MyLog.e("imageUrl", it.toString())
                        }
                        hideLoading()
                    }
                }).addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(e: Exception) {
                        hideLoading()
                    }
                }).addOnProgressListener {
                    showLoading()
                }
        } else {
            val postRequest = PostRequest(
                post_id = post.post_id,
                post_type_id = binding.spinnerPostType.selectedItemPosition + 1,
                theme_id = 1,
                title = binding.edtTitle.text.trim().toString(),
                content = post.content,
                pdf = post.pdf,
                image = post.image,
                description = binding.edtDescription.text.trim().toString(),
                timeline = binding.edtTimeline.text.trim().toString(),
                place = post.place,
                read_count = post.read_count,
                download_count = post.download_count,
                rate_count = post.rate_count,
                date_submitted = post.date_submitted
            )
            viewModel.createPost(postRequest)
        }
    }

    private fun updatePost() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storage =
            FirebaseStorage.getInstance().getReference("/HistoryImage/post_image/" + fileName)

        if (imageUri != null && imageUri.toString().isNotEmpty()) {
            storage.putFile(imageUri!!)
                .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                        storage.downloadUrl.addOnSuccessListener {
                            val postRequest = PostRequest(
                                post_id = post.post_id,
                                post_type_id = binding.spinnerPostType.selectedItemPosition + 1,
                                theme_id = 1,
                                title = binding.edtTitle.text.trim().toString(),
                                content = post.content,
                                pdf = post.pdf,
                                image = it.toString(),
                                description = binding.edtDescription.text.trim().toString(),
                                timeline = binding.edtTimeline.text.trim().toString(),
                                place = post.place,
                                read_count = post.read_count,
                                download_count = post.download_count,
                                rate_count = post.rate_count,
                                date_submitted = post.date_submitted
                            )
                            viewModel.updatePost(postRequest)
                            MyLog.e("imageUrl", it.toString())
                        }
                        hideLoading()
                    }
                }).addOnFailureListener(object : OnFailureListener {
                    override fun onFailure(e: Exception) {
                        hideLoading()
                    }
                }).addOnProgressListener {
                    showLoading()
                }
        } else {
            val postUpdate = PostRequest(
                post_id = post.post_id,
                post_type_id = binding.spinnerPostType.selectedItemPosition + 1,
                theme_id = 1,
                title = binding.edtTitle.text.trim().toString(),
                content = post.content,
                pdf = post.pdf,
                image = post.image,
                description = binding.edtDescription.text.trim().toString(),
                timeline = binding.edtTimeline.text.trim().toString(),
                place = post.place,
                read_count = post.read_count,
                download_count = post.download_count,
                rate_count = post.rate_count,
                date_submitted = post.date_submitted
            )
            viewModel.updatePost(postUpdate)
        }
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            Constants.REQUEST_CODE_POST_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_POST_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Glide.with(requireContext()).load(imageUri).into(binding.ivImage)
        }
    }

    companion object {
        const val KEY_POST = "KEY_POST"
        fun newInstance(post: Post) = PostEditFragment().apply {
            arguments = bundleOf(
                KEY_POST to post
            )
        }
    }
}
