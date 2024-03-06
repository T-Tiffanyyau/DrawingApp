package com.example.drawingactivity

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class DrawingItem(val title: String, var pic: Bitmap)

// It holds the application's UI data in a lifecycle-conscious way, surviving configuration changes like screen rotations.
class DrawingViewModel : ViewModel() {

    private val _selectedDrawing = MutableLiveData<DrawingItem>()
    val selectedDrawing: LiveData<DrawingItem> get() = _selectedDrawing

    // LiveData for pen size
    private val _penSize = MutableLiveData<Float>()
    val penSize: LiveData<Float> get() = _penSize

    // LiveData for pen color
    private val _penColor = MutableLiveData<Int>()
    val penColor: LiveData<Int> get() = _penColor

    // LiveData for color choices
    private val _colorChoices = MutableLiveData<List<Int>>()
    val colorChoices: LiveData<List<Int>> get() = _colorChoices

    private var _currentFragment = MutableLiveData<Fragment>()
    val currentFragment: LiveData<Fragment> get() = _currentFragment

    init {
        // Initialize with default values
        _penSize.value = 10f // Default pen size
        _penColor.value = 0xFF000000.toInt() // Default color (black)
        _colorChoices.value =
            listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA)
        _currentFragment.value = SplashScreen()
    }

    /**
     * Update the pen size
     */
    fun setPenSize(size: Float) {
        _penSize.value = size
    }

    /**
     * Update the pen color
     */
    fun setPenColor(color: Int) {
        _penColor.value = color
    }

    /**
     * Update the color choices
     */
    fun addColorChoice(color: Int) {
        val currentList = _colorChoices.value?.toMutableList() ?: mutableListOf()
        currentList.removeAt(0)
        currentList.add(color)
        _colorChoices.value = currentList
    }

    // Create a bitmap and draw on it
    val bitmap: Bitmap = createRedBitmap()

    /**
     * Create a red bitmap
     */
    private fun createRedBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawRect(0f, 0f, 100f, 100f, paint)
        return bitmap
    }

    /**
     * List of drawings for main screen
     */
    private val listOfDrawings = MutableLiveData(
        mutableListOf(
            DrawingItem("Drawing 1", Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)),
            DrawingItem("Drawing 2", bitmap)
        )
    )

    /**
     * Expose the LiveData list of drawings for main screen
     */
    val viewList = listOfDrawings as LiveData<out List<DrawingItem>>

    /**
     * Save a drawing
     */
    fun saveDrawing(name: String, bitmap: Bitmap) {
        listOfDrawings.value!!.add(DrawingItem(name, bitmap))
        listOfDrawings.value = listOfDrawings.value
    }

    /**
     * Set the current fragment
     */
    fun setCurrentFragment(curFrag: Fragment) {
        _currentFragment.value = curFrag
    }

    var _tempSelectedDrawing: Bitmap? = null

    /**
     * Save the unsve drawing on rotation
     */
    fun setUnsaveOnRotation(bitmap: Bitmap?) {
        if (bitmap != null) {
            _tempSelectedDrawing= bitmap
        }
    }

    /**
     * Set the selected drawing
     */
    fun setSelectDrawing(drawing: DrawingItem) {
        _selectedDrawing.value = drawing
    }
}