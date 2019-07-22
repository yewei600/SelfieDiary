package com.ericwei.selfiediary.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.ericwei.selfiediary.InjectorUtils
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.databinding.FragmentPictureGridBinding
import com.ericwei.selfiediary.viewmodels.PictureGridViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PictureGridFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    lateinit var mCurrentPhotoPath: String
    lateinit var mPhotoURI: Uri

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


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    //launch intent to take picture
    private fun onPictureButtonClicked() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {

                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    mPhotoURI = FileProvider.getUriForFile(
                        context!!, "com.ericwei.selfiediary.fileprovider", it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                // val imageBitmap = data!!.extras.get("data") as Bitmap
                this.findNavController().navigate(
                    PictureGridFragmentDirections
                        .actionPictureGridFragmentToConfirmPicAddLocFragment(mPhotoURI)
                )
            } catch (e: NullPointerException) {
                //image not there.
                Toast.makeText(context, "No picture taken", Toast.LENGTH_SHORT).show()
            }
        }
    }
}