package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemVideoChildrenBinding
import com.example.historyvideokotlin.model.Video

class NextVideoAdapter(
    val videoList: List<Video>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NextVideoAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemVideoChildrenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video, context: Context, onItemClickListener: OnItemClickListener) =
            with(binding) {
                binding.run {
                    if (!video.poster_image.isNullOrEmpty()) {
                        Glide.with(context).load(video.poster_image).into(ivPoster)
                    }
                    tvTitle.text = video.title
                    tvCreaterName.text = video.creater
                    tvViewCount.text = video.view_count.toString() + "lượt xem"

                    videoChildrenItem.setOnClickListener {
                        onItemClickListener.onPlay(video)
                    }

                    ivMore.setOnClickListener {
                        onItemClickListener.onMore(video.video_id)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoChildrenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoList[position], context, onItemClickListener)
    }

    override fun getItemCount(): Int = videoList.size

    interface OnItemClickListener {
        fun onPlay(video: Video)
        fun onMore(videoId: Int)
    }
}