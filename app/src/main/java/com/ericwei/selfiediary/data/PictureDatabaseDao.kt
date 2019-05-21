package com.ericwei.selfiediary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PictureDatabaseDao {

    //insert a new picture
    @Insert
    fun insert(picture: Picture)

    //delete a picture
    @Delete
    fun delete(picture: Picture)

    //get a particular picture by id
    @Query("SELECT * from daily_picture_table WHERE picId = :key")
    fun get(key: Long): Picture?

    //get all the pictures from table, in descending order
    @Query("SELECT * FROM daily_picture_table ORDER BY picId DESC")
    fun getAllPictures(): LiveData<List<Picture>>

}