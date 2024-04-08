package com.example.drawingactivity

import android.graphics.Bitmap
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DrawingDaoTest {

    private lateinit var database: DrawingDatabase
    private lateinit var dao: DrawingDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, DrawingDatabase::class.java).build()
        dao = database.DrawingDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertAndRetrieveDrawingTest() {
        runBlocking {
            // Given a DrawingData
            val drawing = DrawingData("Test Drawing", Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))

            // When inserting a drawing into the Dao
            dao.addFunfactData(drawing)

            // Then the drawing can be retrieved from the Dao
            val retrievedDrawing = dao.allFunFacts().first().find { it.title == drawing.title }

            assertEquals(drawing.title, retrievedDrawing?.title)
        }
    }


    @Test
    fun countDrawingsByNameTest() {
        runBlocking {
            // Given a DrawingData
            val drawing = DrawingData("Test Drawing", Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))

            // When inserting a drawing into the Dao
            dao.addFunfactData(drawing)

            // Then the count of drawings with the same name should be 1
            val count = dao.countDrawingsByName(drawing.title)

            assertEquals(1, count)
        }
    }

    @Test
    fun deleteDrawingByTitleTest() {
        runBlocking {
            // Given a DrawingData
            val drawing = DrawingData("Test Drawing", Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))
    
            // When inserting a drawing into the Dao
            dao.addFunfactData(drawing)

            // And then deleting the drawing by title
            dao.deleteDrawingByTitle(drawing.title)

            // Then the drawing should not be retrievable from the Dao
            val retrievedDrawing = dao.allFunFacts().first().find { it.title == drawing.title }

            assertEquals(null, retrievedDrawing)
        }
}



}