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
import androidx.lifecycle.Observer
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

class PictureGridFragment : Fragment(), View.OnClickListener {

    private val mViewModel: PictureGridViewModel by lazy {
        ViewModelProviders.of(
            this, InjectorUtils.providePictureGridViewModelFactory(requireContext())
        ).get(PictureGridViewModel::class.java)
    }
    private val REQUEST_IMAGE_CAPTURE = 1
    lateinit var mCurrentPhotoPath: String
    lateinit var mPhotoURI: Uri
    lateinit var mAdapter: PictureGridAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPictureGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_picture_grid, container, false
        )
        binding.lifecycleOwner = this

        mAdapter = PictureGridAdapter(PictureGridAdapter.OnPictureClickListener {
            //Toast.makeText(context, "clicked picture=" + it.picId, Toast.LENGTH_SHORT).show()
            mViewModel.displaySelectedPicture(it)
        })

        binding.photosGrid.adapter = mAdapter

        binding.pictureBtn.setOnClickListener(this)
        binding.settingBtn.setOnClickListener(this)
        binding.playBtn.setOnClickListener(this)

        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {

        mViewModel.pictures.observe(this, Observer {
            //Toast.makeText(context, "number of pictures=" + mViewModel.pictures.value!!.size, Toast.LENGTH_SHORT).show()
            mAdapter.submitList(it)
        })

        mViewModel.navigateToSelectedPicture.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(PictureGridFragmentDirections.actionPictureGridFragmentToDetailPictureFragment(it))
                mViewModel.displaySelectedPictureComplete()
            }
        })
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

    private fun onPlayButtonClicked() {
        this.findNavController().navigate(
            PictureGridFragmentDirections.actionPictureGridFragmentToVideoConfigFragment()
        )
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

    private fun onSettingsButtonClicked() {
        this.findNavController().navigate(
            PictureGridFragmentDirections.actionPictureGridFragmentToSettingsFragment()
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.picture_btn -> onPictureButtonClicked()
            R.id.play_btn -> onPlayButtonClicked()
            R.id.setting_btn -> onSettingsButtonClicked()
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