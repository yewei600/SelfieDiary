package com.ericwei.selfiediary.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class PicturesRepository(private val databaseDao: PictureDatabaseDao) {

    suspend fun savePicture(picture: Picture) {
        withContext(IO) {
            databaseDao.insert(picture)
        }
    }

    fun getPictures() = databaseDao.getAllPictures()

    companion object {

        @Volatile
        private var INSTANCE: PicturesRepository? = null

        fun getInstance(databaseDao: PictureDatabaseDao) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PicturesRepository(databaseDao).also {
                INSTANCE = it
            }
        }
    }

}