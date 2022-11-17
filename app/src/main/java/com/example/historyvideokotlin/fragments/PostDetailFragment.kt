package com.example.historyvideokotlin.fragments

import android.net.Uri
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.AsyncTask
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.base.BaseFragment
import com.example.historyvideokotlin.databinding.FragmentPostDetailBinding
import com.example.historyvideokotlin.model.Post
import com.example.historyvideokotlin.viewmodels.PostDetailViewModel
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

class PostDetailFragment : BaseFragment<PostDetailViewModel, FragmentPostDetailBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_post_detail

    override fun getViewModel(): PostDetailViewModel =
        ViewModelProvider(requireActivity()).get(PostDetailViewModel::class.java)

    override fun initData() {
        val post = arguments?.getSerializable(POST_DATA_KEY) as Post
        viewModel.updatePostRead(post.post_id)

        binding.post = post

        if (post.pdf.isNotEmpty()) {
//            binding.wvContent.run {
//                webViewClient = WebViewClient()
//                settings.setSupportZoom(true)
//                settings.javaScriptEnabled = true
//                loadUrl(post.pdf)
//            }
//            RetrivePDFfromUrl(binding.pvContent).execute(post.pdf)
//            binding.pvContent.fromUri(Uri.parse(post.pdf))
//                .enableAnnotationRendering(true)
//                .scrollHandle(DefaultScrollHandle(requireContext()))
//                .spacing(10) // in dp
//                .load()
        }

        binding.ratingBar.setOnRatingBarChangeListener(
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                viewModel.updatePostRate(post.post_id)
            }
        )

        binding.ivBack.setOnClickListener {
            back()
        }
    }

    override fun onResume() {
        super.onResume()
        hideBottomMenu()
    }

    companion object {

        const val POST_DATA_KEY = "POST_DATA_KEY"

        @JvmStatic
        fun newInstance(post: Post) =
            PostDetailFragment().apply {
                arguments = bundleOf(POST_DATA_KEY to post)
            }
    }

    internal class RetrivePDFfromUrl(val pdfView: PDFView) :
        AsyncTask<String?, Void?, InputStream?>() {

        override fun onPostExecute(inputStream: InputStream?) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load()
        }

        override fun doInBackground(vararg p0: String?): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(strings[0])
                // below is the step where we are
                // creating our connection.
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.getResponseCode() === 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = BufferedInputStream(urlConnection.getInputStream())
                }
            } catch (e: IOException) {
                // this is the method
                // to handle errors.
                e.printStackTrace()
                return null
            }
            return inputStream
        }
    }
}
