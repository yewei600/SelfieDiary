package com.ericwei.selfiediary.ui

import android.os.Bundle
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPictureGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_picture_grid, container, false
        )
        val mViewModel = ViewModelProviders.of(
            this, InjectorUtils.providePictureGridViewModelFactory(
                requireContext()
            )
        )
            .get(PictureGridViewModel::class.java)
        val adapter = PictureGridAdapter()

        return binding.root
    }

    private fun subscribeUi() {
//        mViewModel.pictures.observe(viewLifecycleOwner, Observer {
//
//        })
    }

}