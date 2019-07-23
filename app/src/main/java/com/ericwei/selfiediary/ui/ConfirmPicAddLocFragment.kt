package com.ericwei.selfiediary.ui

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ericwei.selfiediary.InjectorUtils
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.data.Picture
import com.ericwei.selfiediary.databinding.FragmentConfirmPicAddLocBinding
import com.ericwei.selfiediary.viewmodels.ConfirmPicAddLocViewModel
import java.io.File

class ConfirmPicAddLocFragment : Fragment() {

    lateinit var mPhotoURI: Uri
    private val mViewModel: ConfirmPicAddLocViewModel by lazy {
        ViewModelProviders.of(this, InjectorUtils.providePictureGridViewModelFactory(requireContext()))
            .get(ConfirmPicAddLocViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentConfirmPicAddLocBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_confirm_pic_add_loc, container, false
        )
        binding.lifecycleOwner = this

        mPhotoURI = ConfirmPicAddLocFragmentArgs.fromBundle(arguments!!).pictureTaken
        binding.pictureTaken.setImageBitmap(MediaStore.Images.Media.getBitmap(context!!.contentResolver, mPhotoURI))

        binding.saveBtn.setOnClickListener {
            onSaveButtonClicked()
        }

        return binding.root
    }


    private fun onSaveButtonClicked() {
        //build the object (picture+location) and save to database
        val picture = Picture(
            picDate = "04/07/2019",
            picTime = "12:30pm",
            picLocation = "North America",
            imageUrl = mPhotoURI.toString()
        )

        mViewModel.savePicture(picture)

        this.findNavController()
            .navigate(ConfirmPicAddLocFragmentDirections.actionConfirmPicAddLocFragmentToPictureGridFragment())
    }

    private fun deletePhoto() {
        var photo = File(mPhotoURI.path)
        if (photo.exists()) {
            photo.delete()
        }
    }


    //on back pressed: have to delete the saved picture from the  Provider
}