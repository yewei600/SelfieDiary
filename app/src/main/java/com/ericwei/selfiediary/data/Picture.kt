package com.ericwei.selfiediary.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_picture_table")
data class Picture(

    @PrimaryKey(autoGenerate = true)
    val picId: Long = 0L,

    @ColumnInfo(name = "pic_date")
    val picDate: String,

    @ColumnInfo(name = "pic_time")
    val picTime: String,

    @ColumnInfo(name = "pic_location")
    val picLocation: String,

    @ColumnInfo(name = "pic_url")
    val imageUrl: String
)