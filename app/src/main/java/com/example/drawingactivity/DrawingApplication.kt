package com.example.drawingactivity

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DrawingApplication : Application(){
    val scope = CoroutineScope(SupervisorJob())

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            DrawingDatabase::class.java,
            "drawing_database"
        ).build()
    }

    val DrawingtRepository by lazy { DrawingRepository(scope, db.DrawingDao()) }
}