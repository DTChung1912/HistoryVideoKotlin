package com.example.historyvideokotlin.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentQuizEditBinding
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.utils.Constants.REQUEST_CODE_QUIZ_IMAGE
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.utils.MyLog
import com.example.historyvideokotlin.viewmodels.QuizEditViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*

class QuizEditFragment : BaseFragment<QuizEditViewModel, FragmentQuizEditBinding>() {

    private var imageUri: Uri? = null
    private lateinit var quiz: Quiz

    override fun getLayoutId() = R.layout.fragment_quiz_edit

    override fun getViewModel() =
        ViewModelProvider(requireActivity())[QuizEditViewModel::class.java]

    override fun initData() {
        binding.viewModel = viewModel

        quiz = arguments?.getSerializable(KEY_QUIZ) as Quiz

        viewModel.setQuiz(quiz)

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
            createQuiz()
        }

        binding.btnUpdate.setOnClickListener {
            updatePost()
        }
    }

    private fun createQuiz() {
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
                            val quizCreate = Quiz(
                                quiz_id = quiz.quiz_id,
                                question = binding.edtTitle.text.trim().toString(),
                                theme_id = binding.spinnerTheme.selectedItemPosition + 1,
                                image = it.toString(),
                                answer_id = quiz.answer_id,
                                correct = binding.edtCorrect.text.trim().toString(),
                                incorrect_1 = binding.edtIncorrect1.text.trim().toString(),
                                incorrect_2 = binding.edtIncorrect2.text.trim().toString(),
                                incorrect_3 = binding.edtIncorrect3.text.trim().toString(),
                                date_submitted = HistoryUtils.getCurrentDateAndTime()
                            )
                            viewModel.createQuiz(quizCreate)
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
            val quizCreate = Quiz(
                quiz_id = quiz.quiz_id,
                question = binding.edtTitle.text.trim().toString(),
                theme_id = binding.spinnerTheme.selectedItemPosition + 1,
                image = quiz.image,
                answer_id = quiz.answer_id,
                correct = binding.edtCorrect.text.trim().toString(),
                incorrect_1 = binding.edtIncorrect1.text.trim().toString(),
                incorrect_2 = binding.edtIncorrect2.text.trim().toString(),
                incorrect_3 = binding.edtIncorrect3.text.trim().toString(),
                date_submitted = HistoryUtils.getCurrentDateAndTime()
            )
            viewModel.createQuiz(quizCreate)
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
                            val quizCreate = Quiz(
                                quiz_id = quiz.quiz_id,
                                question = binding.edtTitle.text.trim().toString(),
                                theme_id = binding.spinnerTheme.selectedItemPosition + 1,
                                image = it.toString(),
                                answer_id = quiz.answer_id,
                                correct = binding.edtCorrect.text.trim().toString(),
                                incorrect_1 = binding.edtIncorrect1.text.trim().toString(),
                                incorrect_2 = binding.edtIncorrect2.text.trim().toString(),
                                incorrect_3 = binding.edtIncorrect3.text.trim().toString(),
                                date_submitted = HistoryUtils.getCurrentDateAndTime()
                            )
                            viewModel.createQuiz(quizCreate)
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
            val quizCreate = Quiz(
                quiz_id = quiz.quiz_id,
                question = binding.edtTitle.text.trim().toString(),
                theme_id = binding.spinnerTheme.selectedItemPosition + 1,
                image = quiz.image,
                answer_id = quiz.answer_id,
                correct = binding.edtCorrect.text.trim().toString(),
                incorrect_1 = binding.edtIncorrect1.text.trim().toString(),
                incorrect_2 = binding.edtIncorrect2.text.trim().toString(),
                incorrect_3 = binding.edtIncorrect3.text.trim().toString(),
                date_submitted = HistoryUtils.getCurrentDateAndTime()
            )
            viewModel.createQuiz(quizCreate)
        }
    }

    private fun openImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            REQUEST_CODE_QUIZ_IMAGE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_QUIZ_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            Glide.with(requireContext()).load(imageUri).into(binding.ivImage)
        }
    }

    companion object {
        const val KEY_QUIZ = "KEY_QUIZ"
        fun newInstance(quiz: Quiz) = QuizEditFragment().apply {
            arguments = bundleOf(
                KEY_QUIZ to quiz
            )
        }
    }
}
