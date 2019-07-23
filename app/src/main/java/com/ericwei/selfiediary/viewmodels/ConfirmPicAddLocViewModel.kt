package com.ericwei.selfiediary.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericwei.selfiediary.data.Picture
import com.ericwei.selfiediary.data.PicturesRepository
import kotlinx.coroutines.launch

class ConfirmPicAddLocViewModel(private val picturesRepository: PicturesRepository) : ViewModel() {


    fun savePicture(picture: Picture) {
        viewModelScope.launch {
            picturesRepository.savePicture(picture)
        }
    }


}