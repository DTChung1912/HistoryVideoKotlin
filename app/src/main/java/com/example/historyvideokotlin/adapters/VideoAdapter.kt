package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemVideoBinding
import com.example.historyvideokotlin.model.Video

class VideoAdapter(val videoList : List<Video>,val context: Context, val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video, context: Context, onItemClickListener: OnItemClickListener) = with(binding) {
            binding.tvTitle.text = video.title
            if (!video.poster_image.isNullOrEmpty() ) {
                Glide.with(context).load(video.poster_image).into(binding.ivPoster)
            }
            binding.tvEmojiCount.text = video.emoji_count.toString()
            binding.tvVewCount.text = video.view_count.toString()
            binding.tvCommentCount.text = video.comment_count.toString()
            binding.tvShareCount.text = video.share_count.toString()
            binding.flPlay.setOnClickListener {
                onItemClickListener.onItemClick(video)
            }
            binding.llEmoji.setOnClickListener {

            }
            binding.llComment.setOnClickListener {

            }
            binding.llShare.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoList[position], context, onItemClickListener)
    }

    override fun getItemCount(): Int = videoList.size

    interface OnItemClickListener {
        fun onItemClick(video: Video)
    }
}