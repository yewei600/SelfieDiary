package com.ericwei.selfiediary

import android.content.Context
import com.ericwei.selfiediary.data.AppDatabase
import com.ericwei.selfiediary.data.PicturesRepository
import com.ericwei.selfiediary.viewmodels.ViewModelFactory

object InjectorUtils {

    private fun getPicturesRepository(context: Context): PicturesRepository {
        return PicturesRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).pictureDatabaseDao
        )
    }

    fun providePictureGridViewModelFactory(context: Context): ViewModelFactory {
        val repository = getPicturesRepository(context)
        return ViewModelFactory(repository)
    }
}