package com.ericwei.selfiediary.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ericwei.selfiediary.databinding.FragmentDatesPickerBinding

class DatesPickerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDatesPickerBinding.inflate(inflater)

        return binding.root
    }
}
