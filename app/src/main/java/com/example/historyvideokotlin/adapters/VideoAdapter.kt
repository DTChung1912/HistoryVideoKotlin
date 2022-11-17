package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemVideoBinding
import com.example.historyvideokotlin.model.Video

class VideoAdapter(
    val videoList: List<Video>,
    val context: Context,
    val itemListener: ItemListener
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video, context: Context, itemListener: ItemListener) =
            with(binding) {
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
                    itemListener.onMore(video.video_id)
                }

                flPlay.setOnClickListener {
                    itemListener.onItemClick(video)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoList[position], context, itemListener)
    }

    override fun getItemCount(): Int = videoList.size

    interface ItemListener {
        fun onItemClick(video: Video)
        fun onMore(videoId: Int)
    }
}
