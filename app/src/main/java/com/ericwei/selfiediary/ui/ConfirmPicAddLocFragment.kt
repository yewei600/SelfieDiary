package com.ericwei.selfiediary.ui

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.databinding.FragmentConfirmPicAddLocBinding

class ConfirmPicAddLocFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentConfirmPicAddLocBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_confirm_pic_add_loc, container, false
        )

        // val viewModel = ViewModelProviders.of(this).get(ConfirmV)

        var args = ConfirmPicAddLocFragmentArgs.fromBundle(arguments!!).pictureTaken
        binding.pictureTaken.setImageBitmap(MediaStore.Images.Media.getBitmap(context!!.contentResolver, args))

        binding.saveBtn.setOnClickListener {
            onSaveButtonClicked()
        }

        return binding.root
    }


    private fun onSaveButtonClicked() {
        //build the object (picture+location) and save to database

        this.findNavController()
            .navigate(ConfirmPicAddLocFragmentDirections.actionConfirmPicAddLocFragmentToPictureGridFragment())
    }
}