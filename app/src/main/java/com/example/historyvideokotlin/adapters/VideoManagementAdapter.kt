package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemVideoManagementBinding
import com.example.historyvideokotlin.model.Video
import com.example.historyvideokotlin.utils.slideDown

class VideoManagementAdapter(val list: List<Video>, val listener: ItemListener) :
    RecyclerView.Adapter<VideoManagementAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemVideoManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isShow = true
        fun bind(video: Video, listener: ItemListener) {
            binding.video = video
            binding.item.setOnClickListener {
                if (isShow) {
                     binding.llMore.slideDown()
                } else {
 //                    binding.llMore.slideUp()
                }
                binding.llMore.visibility = if (isShow) VISIBLE else GONE
                isShow = !isShow
            }
            binding.ivEdit.setOnClickListener {
                listener.onEdit(video)
            }
            binding.ivDelete.setOnClickListener {
                listener.onDelete(video.video_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoManagementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount() = list.size

    interface ItemListener {
        fun onClick()
        fun onEdit(video: Video)
        fun onDelete(videoId: Int)
    }
}
