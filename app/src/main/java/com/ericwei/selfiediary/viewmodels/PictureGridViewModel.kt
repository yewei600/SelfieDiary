package com.ericwei.selfiediary.viewmodels

import androidx.lifecycle.ViewModel
import com.ericwei.selfiediary.data.PicturesRepository

class PictureGridViewModel(picturesRepository: PicturesRepository) : ViewModel() {

    val pictures = picturesRepository.getPictures()
}