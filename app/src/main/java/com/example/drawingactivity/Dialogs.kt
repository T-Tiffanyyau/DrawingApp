package com.example.drawingactivity

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

/**
 * Helper class for showing dialogs
 */
object Dialogs {
    /**
     * Show a dialog for creating a custom color
     */
    fun showCustomColorDialog(fragment: Fragment, drawingViewModel: DrawingViewModel) {
        val builder = AlertDialog.Builder(fragment.requireContext())
        val inflater = fragment.layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_custom_color, null)

        val editTextRed = dialogLayout.findViewById<EditText>(R.id.editTextRed)
        val editTextGreen = dialogLayout.findViewById<EditText>(R.id.editTextGreen)
        val editTextBlue = dialogLayout.findViewById<EditText>(R.id.editTextBlue)
        val buttonCreateColor = dialogLayout.findViewById<Button>(R.id.buttonCreateColor)

        builder.setView(dialogLayout)
        builder.setTitle("Create Custom Color")

        val dialog = builder.create()

        buttonCreateColor.setOnClickListener {
            val red = if (editTextRed.text.toString().isEmpty()) 0 else editTextRed.text.toString()
                .toInt()
            val green =
                if (editTextGreen.text.toString().isEmpty()) 0 else editTextGreen.text.toString()
                    .toInt()
            val blue =
                if (editTextBlue.text.toString().isEmpty()) 0 else editTextBlue.text.toString()
                    .toInt()
            val color = Color.rgb(red, green, blue)

            drawingViewModel.addColorChoice(color)

            dialog.dismiss()
        }

        dialog.show()
    }

    fun showSaveWarningDialog(
        fragment: Fragment,
        drawingViewModel: DrawingViewModel,
        picTitle: String,
        Bitmap: Bitmap,
        message: String
    ) {
        val builder = AlertDialog.Builder(fragment.activity)
        builder.setTitle("Warning")
        builder.setMessage(message)
        builder.setPositiveButton("Yes") { dialog, which ->
            drawingViewModel.saveDrawing(picTitle, Bitmap)
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Handle negative button action
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}