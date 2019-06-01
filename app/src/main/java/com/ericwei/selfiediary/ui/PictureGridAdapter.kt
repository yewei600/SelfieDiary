package com.ericwei.selfiediary.ui

//class PictureGridAdapter(val onClickListener: OnPictureClickListener) :
//    ListAdapter<Picture, PictureGridAdapter.PictureViewHolder>(DiffCallback) {
//
//    class PictureViewHolder(private var binding: GridViewItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(picture: Picture) {
//            binding.picture_item = picture
//            binding.executePendingBindings()
//        }
//    }
//
//    companion object DiffCallback : DiffUtil.ItemCallback<Picture>() {
//        override fun areItemsTheSame(oldItem: Picture, newItem: Picture): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Picture, newItem: Picture): Boolean {
//            return oldItem.picId == newItem.picId
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
//        return PictureViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
//    }
//
//    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
//        val picture = getItem(position)
//        holder.itemView.setOnClickListener {
//
//        }
//    }
//
//    class OnPictureClickListener(val clickListener: (picture: Picture) -> Unit) {
//        fun onClick(picture: Picture) = clickListener(picture)
//    }
//}