package com.ericwei.selfiediary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ericwei.selfiediary.data.PicturesRepository

class PictureGridViewModelFactory(
    private val repository: PicturesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PictureGridViewModel::class.java)) {
            return PictureGridViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}