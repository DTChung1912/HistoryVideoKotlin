package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemVideoChildrenBinding
import com.example.historyvideokotlin.model.MyVideoRespone
import com.example.historyvideokotlin.model.MyVideoStatus
import com.example.historyvideokotlin.model.Video

class MyVideoAdapter(
    val myVideoRespone: MyVideoRespone,
    val myVideoType: Int,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyVideoAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemVideoChildrenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            video: Video,
            myVideoStatus: MyVideoStatus,
            myVideoType: Int,
            context: Context,
            onItemClickListener: OnItemClickListener
        ) =
            with(binding) {
                binding.run {
                    val seconds = myVideoStatus.duration / 1000
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

                    if (!video.poster_image.isNullOrEmpty()) {
                        Glide.with(context).load(video.poster_image).into(ivPoster)
                    }
                    tvCreaterName.text = video.creater
                    tvTitle.text = video.title
                    tvViewCount.text = video.view_count.toString() + " lượt xem"

                    videoChildrenItem.setOnClickListener {
                        onItemClickListener.onMyVideoPlay(video)
                    }
                    ivMore.setOnClickListener {
                        onItemClickListener.onMyVideoMore(myVideoStatus.my_video_id, myVideoType)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoChildrenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            myVideoRespone.videoList[position],
            myVideoRespone.myVideoStatusList[position],
            myVideoType,
            context,
            onItemClickListener
        )
    }

    override fun getItemCount(): Int = myVideoRespone.size

    interface OnItemClickListener {
        fun onMyVideoPlay(video: Video)
        fun onMyVideoMore(myVideoId: Int, myVideoType: Int)
    }
}
