package com.example.drawingactivity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

@Entity(tableName = "drawing")
data class DrawingData(val title: String, var pic: Bitmap) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // integer primary key for the DB
}

//data class DrawingItem(val title: String, var pic: Bitmap)

class BitmapConverter {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        val immutableBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        return immutableBitmap.copy(immutableBitmap.config, true)
    }
}