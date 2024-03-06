package com.example.drawingactivity

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.GridLayout
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.drawingactivity.databinding.FragmentDrawingScreenBinding
import kotlin.math.min


/**
 * This fragment acts as the UI layer in the MVVM architecture.
 * It's responsible for rendering the CanvasView and interacting with the user.
 * The fragment observes the LiveData from the ViewModel and updates the CanvasView accordingly.
 */
class DrawingScreen : Fragment() {
    private var _binding: FragmentDrawingScreenBinding? = null
    val binding get() = _binding!!
    val drawingViewModel: DrawingViewModel by activityViewModels()
    var clickCallback: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrawingScreenBinding.inflate(layoutInflater)

        observeViewModel()  // Observe ViewModel LiveData
        seekBar()

        // Button listeners for tool bar
        binding.goBackButton.setOnClickListener {
            Log.e("Drawing", "goBackButton Clicked")
            clickCallback()
        }

        binding.saveButton.setOnClickListener {
            Log.e("Drawing", "saveButton Clicked")
            val currentBitmap = binding.canvasView.saveState()
            currentBitmap?.let {
                // TODO: Change the name untitled to user input
                drawingViewModel.saveDrawing("Untitled", it)
            }
        }

        binding.customizeColorButton.setOnClickListener() {
            Dialogs.showCustomColorDialog(this, drawingViewModel)
        }

        // Restore the drawing if available
        if (drawingViewModel.selectedDrawing.value != null) {
            val savedBitmap = drawingViewModel.selectedDrawing.value!!.pic
            savedBitmap?.let {
                binding.canvasView.restoreState(it)
            }
            val title = drawingViewModel.selectedDrawing.value!!.title
            binding.drawingTitle.text = Editable.Factory.getInstance().newEditable(title)
        }
        if (drawingViewModel._tempSelectedDrawing != null) {
            Log.e("Drawing", "a: Restoring temp drawing${drawingViewModel._tempSelectedDrawing}")
            binding.canvasView.restoreState(drawingViewModel._tempSelectedDrawing)
//            drawingViewModel._tempSelectedDrawing = null
        }

        Log.e("Drawing", "b: Drawing Screen Created")
        return binding.root
    }

    /**
     * Observe the ViewModel LiveData
     */
    private fun observeViewModel() {
        drawingViewModel.penSize.observe(viewLifecycleOwner, Observer { size ->
            binding.canvasView.setBrushSize(size)
        })

        drawingViewModel.penColor.observe(viewLifecycleOwner, Observer { color ->
            binding.canvasView.setBrushColor(color)
        })

        drawingViewModel.colorChoices.observe(viewLifecycleOwner, Observer { colors ->
            setupColorSwatches()
        })
    }

    /**
     * Set up the SeekBar to change the pen size
     */
    private fun seekBar() {
        binding.seekBarPenSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Update the pen size in the ViewModel
                val newPenSize = progress.toFloat()
                drawingViewModel.setPenSize(newPenSize)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Implementation not needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Implementation not needed
            }
        })
    }

    /**
     * Set up the color swatches
     */
    private fun setupColorSwatches() {
        val gridLayout: GridLayout = binding.colorSwatchesGrid
        gridLayout.removeAllViews()
        val colors = drawingViewModel.colorChoices.value ?: emptyList()

        val viewTreeObserver = gridLayout.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remove the listener to ensure it's only called once
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    gridLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                } else {
                    gridLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }

                colors.forEach { color ->
                    val colorSwatch = View(context).apply {
                        layoutParams = GridLayout.LayoutParams().apply {
                            var min = min(gridLayout.width, gridLayout.height)
                            width = min
                            height = min
                            setMargins(10, 10, 10, 10)
                        }
                        setBackgroundColor(color)

                        setOnClickListener {
                            // Update the pen color in the ViewModel
                            drawingViewModel.setPenColor(color)
                        }
                    }

                    gridLayout.addView(colorSwatch)
                }
            }
        })
    }

    /**
     * Set the listener for the goBackButton
     */
    public fun setListener(listener: () -> Unit) {
        clickCallback = listener
    }

    /**
     * Save the current state of the canvas
     */
    fun saveCanvasState(): Bitmap? {
        return binding.canvasView.saveState()
    }
}