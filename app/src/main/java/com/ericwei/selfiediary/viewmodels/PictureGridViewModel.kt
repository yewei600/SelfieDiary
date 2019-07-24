package com.ericwei.selfiediary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ericwei.selfiediary.data.Picture
import com.ericwei.selfiediary.data.PicturesRepository

class PictureGridViewModel(picturesRepository: PicturesRepository) : ViewModel() {

    val pictures: LiveData<List<Picture>> = picturesRepository.getPictures()

    val navigateToSelectedPicture: LiveData<Picture>
        get() = _navigateToSelectedPicture

    private val _navigateToSelectedPicture = MutableLiveData<Picture>()

    fun displaySelectedPicture(picture: Picture) {
        _navigateToSelectedPicture.value = picture
    }

    fun displaySelectedPictureComplete() {
        _navigateToSelectedPicture.value = null
    }
}