package com.ericwei.selfiediary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Picture::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val pictureDatabaseDao: PictureDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "pictures_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}