package com.ericwei.selfiediary

import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ericwei.selfiediary.data.Picture
import com.ericwei.selfiediary.ui.PictureGridAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Picture>?) {
    val adapter = recyclerView.adapter as PictureGridAdapter
    adapter.submitList(data)
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    val bitmap = MediaStore.Images.Media.getBitmap(imgView.context.contentResolver, Uri.parse(imgUrl))
    imgView.setImageBitmap(bitmap)
}

