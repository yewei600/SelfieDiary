package com.ericwei.selfiediary.data

class PicturesRepository(private val databaseDao: PictureDatabaseDao) {

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