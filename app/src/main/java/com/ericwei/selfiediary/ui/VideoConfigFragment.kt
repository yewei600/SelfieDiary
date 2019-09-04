package com.ericwei.selfiediary.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ericwei.selfiediary.R
import com.ericwei.selfiediary.databinding.FragmentVideoConfigBinding
import com.ericwei.selfiediary.viewmodels.MakeVideoStatus
import com.ericwei.selfiediary.viewmodels.VideoConfigViewModel
import kotlinx.android.synthetic.main.fragment_video_config.*


/**
 * A simple [Fragment] subclass.
 *
 */
class VideoConfigFragment : Fragment(), View.OnClickListener {

    val TAG = "VideoConfigFragment"
    private val mViewModel = VideoConfigViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentVideoConfigBinding.inflate(inflater)

        binding.usingPhotosBtn.setOnClickListener(this)

        binding.createVideoBtn.setOnClickListener(this)

        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        mViewModel.status.observe(this, Observer { videoStatus ->
            when (videoStatus) {
                MakeVideoStatus.IN_PROGRESS ->
                    //start video making in progress UI
                    Log.d(TAG, "In_Progress")
                MakeVideoStatus.DONE ->
                    //end in progress UI
                    Log.d(TAG, "Video making done")
            }

        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.using_photos_btn ->
                this.findNavController().navigate(
                    VideoConfigFragmentDirections.actionVideoConfigFragmentToDatesPickerFragment()
                )
            R.id.create_video_btn ->
                if (fpsEditText.text.isEmpty() || !fpsEditText.text.isDigitsOnly()) {
                    Toast.makeText(context, "FPS info not valid", Toast.LENGTH_SHORT).show()
                } else {
                    //start service to make the video
                    mViewModel.startMakingVideo()
                }
        }
    }
}
