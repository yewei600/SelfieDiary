package com.ericwei.selfiediary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ericwei.selfiediary.data.Picture
import com.ericwei.selfiediary.databinding.GridViewItemBinding
import com.ericwei.selfiediary.databinding.GridViewItemBinding.inflate

class PictureGridAdapter(val onClickListener: OnPictureClickListener) :
    ListAdapter<Picture, PictureGridAdapter.PictureViewHolder>(DiffCallback) {

    class PictureViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(picture: Picture) {
            binding.pictureItem = picture
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Picture>() {
        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
            return oldItem.picId == newItem.picId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val picture = getItem(position)
        holder.itemView.setOnClickListener {

        }
        holder.bind(picture)
    }

    class OnPictureClickListener(val clickListener: (picture: Picture) -> Unit) {
        fun onClick(picture: Picture) = clickListener(picture)
    }
}