package com.ericwei.selfiediary.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ericwei.selfiediary.InjectorUtils
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.databinding.FragmentPictureGridBinding
import com.ericwei.selfiediary.viewmodels.PictureGridViewModel

class PictureGridFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPictureGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_picture_grid, container, false
        )

        val viewModel = ViewModelProviders.of(
            this, InjectorUtils.providePictureGridViewModelFactory(requireContext())
        ).get(PictureGridViewModel::class.java)

        //binding.photosGrid.adapter = PictureGridAdapter()

        binding.pictureBtn.setOnClickListener {
            onPictureButtonClicked()
        }

        return binding.root
    }

    private fun subscribeUi() {
//        mViewModel.pictures.observe(viewLifecycleOwner, Observer {
//
//        })
    }

    //launch intent to take picture
    private fun onPictureButtonClicked() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                val imageBitmap = data!!.extras.get("data") as Bitmap
                this.findNavController().navigate(
                    PictureGridFragmentDirections
                        .actionPictureGridFragmentToConfirmPicAddLocFragment(imageBitmap)
                )
            } catch (e: NullPointerException) {
                //image not there.
                Toast.makeText(context, "No picture taken", Toast.LENGTH_SHORT).show()
            }
        }
    }
}