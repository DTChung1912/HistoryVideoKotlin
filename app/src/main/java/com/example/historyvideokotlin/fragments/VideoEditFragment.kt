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
import com.example.historyvideokotlin.databinding.FragmentVideoEditBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.Constants
import com.example.historyvideokotlin.utils.Constants.REQUEST_CODE_VIDEO_CREATER
import com.example.historyvideokotlin.utils.Constants.REQUEST_CODE_VIDEO_POSTER
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.VideoEditViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class VideoEditFragment : BaseFragment<VideoEditViewModel, FragmentVideoEditBinding>() {

    var storageReference: StorageReference? = null
    private var posterUri: Uri? = null
    private var createrUri: Uri? = null

    private lateinit var video: Video

    override fun getLayoutId() = R.layout.fragment_video_edit

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[VideoEditViewModel::class.java]

    override fun initData() {
        binding.viewModel = viewModel
        video = arguments?.getSerializable(KEY_VIDEO) as Video

        binding.toolbar.title = if (video.video_id == 0) "Thêm mới Video" else "Cập nhật Video"

        viewModel.setIsUpdate(video)
        viewModel.getThemeList()
        viewModel.themeList.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(requireContext(), R.layout.list_option, it)
            binding.spinnerTheme.setAdapter(adapter)
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

        binding.btnPoster.setOnClickListener {
            openImagePoster()
        }

        binding.btnCreaterImage.setOnClickListener {
            openImageCreater()
        }

        binding.btnAdd.setOnClickListener {
            val video = Video(
                video_id = 0,
                title = binding.edtTitle.text.toString(),
                theme_id = 1,
                creater_image = "",
                creater = binding.edtCreater.text.toString(),
                platform = video.platform,
                like_count = video.like_count,
                view_count = video.view_count,
                dislike_count = video.dislike_count,
                comment_count = video.comment_count,
                poster_image = video.poster_image,
                date_submitted = HistoryUtils.getCurrentDateAndTime()
            )
            viewModel.createVideo(video)
        }

        binding.btnUpdate.setOnClickListener {
            updateVideo()
        }
    }

    private fun updateVideo() {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        val storagePoster = FirebaseStorage.getInstance().getReference("PosterVideo/" + fileName)
        val storageCreater = FirebaseStorage.getInstance().getReference("CreaterImage/" + fileName)

        if (posterUri != null) {
            storagePoster.putFile(posterUri!!)
                .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                    override fun onSuccess(snapshot: UploadTask.TaskSnapshot?) {
                        storagePoster.downloadUrl.addOnSuccessListener {
                            val video = Video(
                                video_id = video.video_id,
                                title = binding.edtTitle.text.trim().toString(),
                                theme_id = 1,
                                creater_image = "",
                                creater = binding.edtCreater.text.trim().toString(),
                                platform = video.platform,
                                like_count = video.like_count,
                                view_count = video.view_count,
                                dislike_count = video.dislike_count,
                                comment_count = video.comment_count,
                                poster_image = it.toString(),
                                date_submitted = HistoryUtils.getCurrentDateAndTime()
                            )
                            viewModel.updateVideo(video)
                            MyLog.e("imageUrl", it.toString())
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
        } else {
            val video = Video(
                video_id = video.video_id,
                title = binding.edtTitle.text.trim().toString(),
                theme_id = 1,
                creater_image = "",
                creater = binding.edtCreater.text.trim().toString(),
                platform = video.platform,
                like_count = video.like_count,
                view_count = video.view_count,
                dislike_count = video.dislike_count,
                comment_count = video.comment_count,
                poster_image = video.poster_image,
                date_submitted = HistoryUtils.getCurrentDateAndTime()
            )
            viewModel.updateVideo(video)
        }
    }

    private fun openImagePoster() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_CODE_VIDEO_POSTER
        )
    }

    private fun openImageCreater() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_CODE_VIDEO_CREATER
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_VIDEO_POSTER && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            posterUri = data.data
            Glide.with(requireContext()).load(posterUri).into(binding.ivPoster)
        } else if (requestCode == REQUEST_CODE_VIDEO_CREATER && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            createrUri = data.data
            Glide.with(requireContext()).load(createrUri)
                .into(binding.ivCreaterAvatar)
        }
    }

    companion object {
        const val KEY_VIDEO = "KEY_VIDEO"
        fun newInstance(video: Video) = VideoEditFragment().apply {
            arguments = bundleOf(
                KEY_VIDEO to video
            )
        }
    }
}
