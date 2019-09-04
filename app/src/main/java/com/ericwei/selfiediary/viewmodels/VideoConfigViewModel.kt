package com.ericwei.selfiediary.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


enum class MakeVideoStatus {
    INACTIVE,
    IN_PROGRESS,
    DONE
}

class VideoConfigViewModel() : ViewModel() {

    val status: LiveData<MakeVideoStatus>
        get() = _status

    private val _status = MutableLiveData<MakeVideoStatus>().apply {
        value = MakeVideoStatus.INACTIVE
    }

    fun startMakingVideo() {
        _status.value = MakeVideoStatus.IN_PROGRESS
    }

    fun finishMakingVideo() {
        _status.value = MakeVideoStatus.DONE
    }
}