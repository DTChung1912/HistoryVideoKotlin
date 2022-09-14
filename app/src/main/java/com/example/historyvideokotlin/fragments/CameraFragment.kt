package com.example.historyvideokotlin.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.AppConfigs.BASE_ENDPOINT_URL.SAVE_AUDIO_FOLDER
import com.example.historyvideokotlin.base.AppEvent
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentCameraBinding
import com.example.historyvideokotlin.listener.SwitchFragmentListener
import com.example.historyvideokotlin.utils.HistoryUtils
import com.example.historyvideokotlin.viewmodels.CameraViewModel
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Flash
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : BaseFragment<CameraViewModel, FragmentCameraBinding>(),
    View.OnClickListener {


    private var mCameraView: CameraView? = null
    private val clickTimeStart: Long = 0
    private var isPickLastImage = false

    private var listener: OnClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_camera
    }

    override fun getViewModel(): CameraViewModel =
        ViewModelProvider(requireActivity()).get(CameraViewModel::class.java)

    override fun getAnalyticsScreenName(): String? = null

    override fun initData() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().viewModel = getViewModel()
        if (context == null || activity == null) {
            return
        }
        disableButton()
        checkPermission()
        getBinding().imageRotate.setOnClickListener(this)
        getBinding().imageFlash.setOnClickListener(this)
        getBinding().imageCapture.setOnClickListener(this)
        getBinding().imagePreview.setOnClickListener(this)
        getBinding().imageClose.setOnClickListener(this)
    }

    private fun initCamera() {
        mCameraView = binding.camera
        mCameraView!!.setLifecycleOwner(viewLifecycleOwner)
        mCameraView!!.addCameraListener(Listener())
    }

    private fun requestCameraPermission() {
        requestPermissions(
            CAMERA_PERMISSION,
            REQUEST_CAMERA_PERMISSION
        )
    }

    @SuppressLint("Range")
    private fun pickLastImage() {
        isPickLastImage = true
        val cursor = requireContext().contentResolver
            .query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Images.ImageColumns.DATE_ADDED + " DESC"
            )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val imageLocation =
                    cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))
                val imageFile = File(imageLocation)
                if (imageFile.exists()) {
                    try {
                        val exif = ExifInterface(imageLocation)
                        val orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED
                        )
                        val bitmap =
                            rotateBitmap(BitmapFactory.decodeFile(imageLocation), orientation)
                        Glide.with(getBinding().imagePreview)
                            .load(bitmap)
                            .circleCrop()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(getBinding().imagePreview)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    getBinding().imagePreview.setImageResource(R.drawable.ic_no_image_in_gallery)
                }
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestCameraPermission()
        } else {
            initCamera()
            enableButton()
            pickLastImage()
        }
    }

    private fun takePicture() {
        if (mCameraView!!.isTakingPicture) return
        mCameraView!!.takePicture()
    }

    private fun createFile(size: Int): Uri? {
        val currentTime = System.currentTimeMillis()
        val filename = SimpleDateFormat(CameraFragment.FILENAME, Locale.US)
            .format(currentTime)
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.TITLE, filename)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.MediaColumns.SIZE, size)
        values.put(MediaStore.MediaColumns.DATE_ADDED, currentTime / 1000)
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, currentTime / 1000)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.DATE_TAKEN, currentTime)
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/$SAVE_AUDIO_FOLDER")
        } else {
            val folderPath =
                Environment.getExternalStoragePublicDirectory("image/jpeg").absolutePath + "/" + SAVE_AUDIO_FOLDER
            val folder = File(folderPath)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            values.put(
                MediaStore.MediaColumns.DATA,
                folderPath + "/" + filename + CameraFragment.PHOTO_EXTENSION
            )
        }
        return requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
    }

    private fun switchFlash() {
        getViewModel().setIsEnableTorch()
        mCameraView!!.flash = if (mCameraView!!.flash == Flash.OFF) Flash.ON else Flash.OFF
    }

    private fun enableButton() {
        binding.run {
            imageRotate.isEnabled = true
            imageFlash.isEnabled = true
            imageCapture.isEnabled = true
            viewBackground.visibility = View.GONE
        }
    }

    private fun disableButton() {
        binding.run {
            imageRotate.isEnabled = false
            imageFlash.isEnabled = false
            imageCapture.isEnabled = false
            viewBackground.visibility = View.VISIBLE
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap? {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            else -> return bitmap
        }
        val bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return bmRotated
    }

//    private fun hasCameraPermission(): Boolean {
//        return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA)
//    }
//
//    private fun hasMemoryPermission(): Boolean {
//        return EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    }
//
//    private fun showDialogRequestCamera() {
//        if (activity != null) {
//            Builder(activity)
//                .setCancelable(false)
//                .setTitle(getString(R.string.setting_camera))
//                .setMessage(getString(R.string.request_use_camera))
//                .setPositiveButton(R.string.go_to_setting) { d, i ->
//                    val intent =
//                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                    val uri =
//                        Uri.fromParts("package", activity!!.packageName, null)
//                    intent.data = uri
//                    startActivityForResult(intent, REQUEST_PERMISSION)
//                }
//                .setNegativeButton(R.string.cancel) { d, i -> }
//                .show()
//        }
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            switchFragmentListener = context as SwitchFragmentListener
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onAppEvent(event: AppEvent<String, Objects>) {
    }

    companion object {

        private val MIME_TYPES_IMAGE = arrayOf("image/png", "image/jpg", "image/jpeg")
        private val REQUEST_CODE_PICK_IMAGE = 1
        private val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val PHOTO_EXTENSION = ".jpg"
        private val CAMERA_PERMISSION =
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private var switchFragmentListener: SwitchFragmentListener? = null
        private val REQUEST_CAMERA_PERMISSION = 2
        private val REQUEST_PERMISSION = 97

        @JvmStatic
        fun newInstance(onClickListener: OnClickListener) =
            CameraFragment().apply {
                listener = onClickListener
            }

    }

    interface OnClickListener {
        fun onAvatar(imageUrl: String)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQUEST_CODE_PICK_IMAGE && data != null) {
//                val uri = data.data
//                if (uri != null) {
//                    val type: String = HistoryUtils.getContentType(uri)!!
//                    for (s in MIME_TYPES_IMAGE) {
//                        if (s == type) {
//                            switchFragmentListener!!.onSwitch(uri.toString())
//                            return
//                        }
//                    }
//                }
//                showMessage(
//                    getString(R.string.message_cannot_attach_image),
//                    getString(R.string.message_select_other_image),
//                    R.string.back
//                ) { dialog, which -> dialog.dismiss() }
//            }
//        }
//    }

    override fun onClick(v: View?) {

    }

    inner class Listener : CameraListener() {
        override fun onPictureTaken(result: PictureResult) {
            super.onPictureTaken(result)
            val mFile: Uri = createFile(result.data.size)!!
            saveFile(mFile, result.data)
            switchFragmentListener!!.onSwitch(mFile.toString())
        }

        private fun saveFile(uri: Uri, data: ByteArray) {
            try {
                val stream: OutputStream = BufferedOutputStream(
                    FileOutputStream(
                        requireActivity().getContentResolver().openFileDescriptor(uri, "w")!!
                            .getFileDescriptor()
                    )
                )
                stream.write(data)
                stream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}