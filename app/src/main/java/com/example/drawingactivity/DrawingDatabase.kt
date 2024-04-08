package com.example.drawingactivity

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow


//this is a DB, we have 1 entity (so we'll get 1 table in SQLite)
//the version stuff is for managing DB migrations
@Database(entities = [DrawingData::class], version = 1, exportSchema = false)
//This lets use have an entity with a "Date" in it which Room won't natively support
@TypeConverters(BitmapConverter::class)
abstract class DrawingDatabase : RoomDatabase() {
    abstract fun DrawingDao(): DrawingDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DrawingDatabase? = null


        //When we want a DB we call this (basically static) method
        //val theDB = WeatherDatabase.getDatabase(myContext)
        fun getDatabase(context: Context): DrawingDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                //if another thread initialized this before we got the lock
                //return the object they created
                if (INSTANCE != null) return INSTANCE!!
                //otherwise we're the first thread here, so create the DB
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrawingDatabase::class.java,
                    "drawing_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

@Dao
interface DrawingDao {

    //marked as suspend so the thread can yield in case the DB update is slow
    @Insert
    suspend fun addFunfactData(data: DrawingData)

    @Delete
    suspend fun deleteDrawingData(data: DrawingData)


    //returns a flow, so the task "collecting" it will be marked as suspend
    @Query("SELECT * from drawing ORDER BY id ASC")
    fun allFunFacts(): Flow<List<DrawingData>>

    @Query("SELECT COUNT(*) from drawing WHERE drawing.title = :name")
    suspend fun countDrawingsByName(name: String): Int

    @Query("DELETE FROM drawing WHERE drawing.title = :title")
    suspend fun deleteDrawingByTitle(title: String)
}