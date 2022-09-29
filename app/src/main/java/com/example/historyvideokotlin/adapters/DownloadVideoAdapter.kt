package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemVideoChildrenBinding
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.model.Video

class DownloadVideoAdapter(
    val videoList: List<DownloadVideo>,
    val context: Context,
    val downloadVideoListListener: DownloadVideoListListener
) : RecyclerView.Adapter<DownloadVideoAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemVideoChildrenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            video: DownloadVideo,
            context: Context,
            downloadVideoListListener: DownloadVideoListListener
        ) = with(binding) {
            binding.run {
                if (video.duration != null) {
                    val seconds = video.duration!! / 1000
                    val second = seconds % 60
                    val minute = seconds / 60 % 60
                    val hour = seconds / (60 * 60) % 24
                    var duration = ""
                    if (hour > 0) {
                        duration = String.format("%02d:%02d:%02d", hour, minute, second)
                    } else {
                        duration = String.format("%02d:%02d", minute, second)
                    }

                    tvDuration.text = duration
                }


                if (!video.poster_image.isNullOrEmpty()) {
                    Glide.with(context).load(video.poster_image).into(ivPoster)
                }
                tvCreaterName.text = video.creater
                tvTitle.text = video.title
                tvViewCount.text = video.view_count.toString() + " lượt xem"

                videoChildrenItem.setOnClickListener {
                    downloadVideoListListener.onItemClick(video)
                }
                ivMore.setOnClickListener {
                    downloadVideoListListener.onMore()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoChildrenBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoList[position], context, downloadVideoListListener)
    }

    override fun getItemCount() = videoList.size


}

interface DownloadVideoListListener {
    fun onItemClick(video: DownloadVideo)
    fun onMore()
}