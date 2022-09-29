package com.example.historyvideokotlin.adapters

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemVideoBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.HistoryUtils
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class VideoAdapter(
    val videoList: List<Video>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video,context: Context, onItemClickListener: OnItemClickListener) =
            with(binding) {
                binding.run {
                    tvTitle.text = video.title
                    tvCreaterName.text = video.creater
                    if (!video.poster_image.isNullOrEmpty()) {
                        Glide.with(context).load(video.poster_image).into(ivPoster)
                    }
                    if (!video.creater_image.isNullOrEmpty()) {
                        Glide.with(context).load(video.creater_image).into(civCreaterAvatar)
                    }
                    tvViewCount.text = video.view_count.toString() + " lượt xem"
                    tvDateSubmitted.text = video.date_submitted
                    ivMore.setOnClickListener {
                        onItemClickListener.onMore(video.video_id)
                    }

                    flPlay.setOnClickListener {
                        onItemClickListener.onItemClick(video)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoList[position], context, onItemClickListener)
    }

    override fun getItemCount(): Int = videoList.size

    interface OnItemClickListener {
        fun onItemClick(video: Video)
        fun onMore(videoId: Int)
    }
}
