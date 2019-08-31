package com.ericwei.selfiediary.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.databinding.FragmentVideoConfigBinding


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoConfigFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_video_config, container, false)
        val binding = FragmentVideoConfigBinding.inflate(inflater)
        binding.usingPhotosBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.using_photos_btn -> this.findNavController().navigate(
                VideoConfigFragmentDirections.actionVideoConfigFragmentToDatesPickerFragment()
            )

        }
    }
}
