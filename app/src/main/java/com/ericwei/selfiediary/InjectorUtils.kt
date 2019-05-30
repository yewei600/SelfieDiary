package com.ericwei.selfiediary

import android.content.Context
import com.ericwei.selfiediary.data.AppDatabase
import com.ericwei.selfiediary.data.PicturesRepository
import com.ericwei.selfiediary.viewmodels.PictureGridViewModelFactory

object InjectorUtils {

    private fun getPicturesRepository(context: Context): PicturesRepository {
        return PicturesRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).pictureDatabaseDao
        )
    }

    fun providePictureGridViewModelFactory(context: Context): PictureGridViewModelFactory {
        val repository = getPicturesRepository(context)
        return PictureGridViewModelFactory(repository)
    }
}