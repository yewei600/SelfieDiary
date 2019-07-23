package com.ericwei.selfiediary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ericwei.selfiediary.data.Picture
import com.ericwei.selfiediary.data.PicturesRepository

class PictureGridViewModel(picturesRepository: PicturesRepository) : ViewModel() {

    val pictures: LiveData<List<Picture>> = picturesRepository.getPictures()


}