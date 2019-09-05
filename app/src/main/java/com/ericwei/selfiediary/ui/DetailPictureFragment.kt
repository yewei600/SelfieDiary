package com.ericwei.selfiediary.ui

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ericwei.selfiediary.databinding.FragmentDetailPictureBinding
import com.ericwei.selfiediary.viewmodels.DetailPictureViewModel

class DetailPictureFragment : Fragment() {

    private val mViewModel: DetailPictureViewModel by lazy {
        ViewModelProviders.of(this).get(DetailPictureViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDetailPictureBinding.inflate(inflater)

        binding.lifecycleOwner = this
        val pictureObj = DetailPictureFragmentArgs.fromBundle(arguments!!).selectedPicture

        binding.picTimeDate.setText(pictureObj.picDate + "  " + pictureObj.picTime)

        binding.picLocation.setText(pictureObj.picLocation)

        binding.picLocation.setOnClickListener {
            this.findNavController().navigate(
                DetailPictureFragmentDirections.actionDetailPictureFragmentToPictureLocMapFragment()
            )
        }

        binding.picView.setImageBitmap(
            MediaStore.Images.Media.getBitmap(
                context!!.contentResolver,
                Uri.parse(pictureObj.imageUrl)
            )
        )

        return binding.root

    }

    private fun subscribeUi() {

    }

}
