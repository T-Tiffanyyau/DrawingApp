package com.example.drawingactivity

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

/**
 * Custom view for drawing
 * This is where the actual drawing happens.
 * It handles touch events and draws lines on a canvas based on user input.
 */
class CanvasView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var path = Path()
    private var paint = Paint()
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private var brushSize = 10f
    private var brushColor = Color.BLACK

    init {
        setupPaint()
    }

    /**
     * Set up the paint brush for user to draw.
     */
    private fun setupPaint() {
        paint.color = brushColor
        paint.isAntiAlias = true
        paint.isDither = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = brushSize
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val bitmapWidth = 4000 // Fixed width
        val bitmapHeight = 4000 // Fixed height

        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap!!)

            // Fill the bitmap with a solid color
            bitmap?.eraseColor(Color.WHITE)
        } else {
            val originalBitmap = bitmap
            bitmap = Bitmap.createScaledBitmap(originalBitmap!!, bitmapWidth, bitmapHeight, true)
            canvas = Canvas(bitmap!!)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val sourceRect = Rect(0, 0, width, height) // This is the upper left quarter of the bitmap
        val destRect = Rect(0, 0, width, height) // This is the entire canvas

        // canvas.drawBitmap(bitmap!!, 0f, 0f, null)
        canvas.drawBitmap(bitmap!!, sourceRect, destRect, null)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }

            MotionEvent.ACTION_UP -> {
                canvas?.drawPath(path, paint)
                path.reset()
            }

            else -> return false
        }

        invalidate()
        return true
    }

    /**
     * Set the brush size for drawing.
     */
    fun setBrushSize(size: Float) {
        brushSize = size
        paint.strokeWidth = size
    }

    /**
     * Set the brush color for drawing.
     */
    fun setBrushColor(color: Int) {
        brushColor = color
        paint.color = color
    }

    /**
     * Save the current state of the drawing.
     */
    fun saveState(): Bitmap? {
        return bitmap
    }

    /**
     * Restore the state of the drawing.
     */
    fun restoreState(savedBitmap: Bitmap?) {
        Log.e("Drawing", "Restoring state $savedBitmap")
        savedBitmap?.let {
            bitmap = it
            canvas = Canvas(bitmap!!)
            invalidate()
        }
    }

}
