package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemVideoChildrenBinding
import com.example.historyvideokotlin.model.MyVideo

class MyVideoAdapter(
    val myVideoList: List<MyVideo>,
    val myVideoType: Int,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyVideoAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemVideoChildrenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(myVideo: MyVideo,myVideoType: Int, context: Context, onItemClickListener: OnItemClickListener) =
            with(binding) {
                binding.run {
                    if (!myVideo.poster_image.isNullOrEmpty()) {
                        Glide.with(context).load(myVideo.poster_image).into(ivPoster)
                    }
                    tvCreaterName.text = myVideo.creater
                    tvTitle.text = myVideo.title
                    tvViewCount.text = myVideo.view_count.toString() + "lượt xem"
                    videoChildrenItem.setOnClickListener {
                        onItemClickListener.onMyVideoPlay()
                    }
                    ivMore.setOnClickListener {
                        onItemClickListener.onMyVideoMore(myVideo.my_video_id,myVideoType)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemVideoChildrenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myVideoList[position],myVideoType, context, onItemClickListener)
    }

    override fun getItemCount(): Int = myVideoList.size

    interface OnItemClickListener {
        fun onMyVideoPlay()
        fun onMyVideoMore(myVideoId: Int,myVideoType: Int)
    }

}