package com.ericwei.selfiediary.ui

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ericwei.selfiediary.InjectorUtils
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.databinding.FragmentPictureGridBinding
import com.ericwei.selfiediary.viewmodels.PictureGridViewModel

class PictureGridFragment : Fragment() {

    val REQUEST_IMAGE_CAPTURE = 1

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
    fun onPictureButtonClicked() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}