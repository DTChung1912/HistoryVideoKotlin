package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemThemeManagementBinding
import com.example.historyvideokotlin.model.Theme

class ThemeManagementAdapter(val list: List<Theme>, val listener: ItemListener) :
    RecyclerView.Adapter<ThemeManagementAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemThemeManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(theme: Theme, listener: ItemListener) {
            binding.theme = theme
            binding.ivEdit.setOnClickListener {
                listener.onEdit(theme)
            }
            binding.ivDelete.setOnClickListener {
                listener.onDelete(theme.theme_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemThemeManagementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount() = list.size

    interface ItemListener {
        fun onEdit(theme: Theme)
        fun onDelete(themeId: Int)
    }
}
