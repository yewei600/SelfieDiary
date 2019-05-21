package com.ericwei.selfiediary

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.ericwei.selfiediary.data.AppDatabase
import com.ericwei.selfiediary.data.Picture
import com.ericwei.selfiediary.data.PictureDatabaseDao
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var pictureDatabaseDao: PictureDatabaseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        pictureDatabaseDao = db.pictureDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPicture() {
        val picture = Picture(
            picId = 1,
            picDate = "05/06/2019",
            picTime = "12:30pm",
            picLocation = "Tokyo",
            imageUrl = "image location"
        )
        pictureDatabaseDao.insert(picture)
        val pictureReturned = pictureDatabaseDao.get(key = 1)
        assertEquals(pictureReturned?.picDate, "05/06/2019")
        assertEquals(pictureReturned?.picTime, "12:30pm")
        assertEquals(pictureReturned?.imageUrl, "image location")

    }
}

