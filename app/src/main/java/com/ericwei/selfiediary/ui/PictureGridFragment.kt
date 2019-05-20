package com.ericwei.selfiediary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.databinding.FragmentPictureGridBinding

class PictureGridFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPictureGridBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_picture_grid, container, false
        )

        return binding.root
    }

}