package com.example.drawingactivity

import android.content.res.Configuration
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.drawingactivity.databinding.FragmentDrawingScreenBinding
import kotlinx.coroutines.launch
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
    private var isDragEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrawingScreenBinding.inflate(layoutInflater)
        isDragEnabled = false
        drawingViewModel.toggleDrag(isDragEnabled)

        observeViewModel()  // Observe ViewModel LiveData
        seekBar()
        shapeBar()
        topBar()

        binding.customizeColorButton.setOnClickListener() {
            Dialogs.showCustomColorDialog(this, drawingViewModel)
        }

        // Restore the drawing if available
        if (drawingViewModel.selectedDrawing.value != null) {
            val savedBitmap = drawingViewModel.selectedDrawing.value!!.pic
            savedBitmap?.let {
                // TODO: Scale the drawing with the canvas size
                binding.canvasView.restoreState(savedBitmap)
            }
            val title = drawingViewModel.selectedDrawing.value!!.title
            binding.drawingTitle.text = Editable.Factory.getInstance().newEditable(title)
        }
        if (drawingViewModel._tempSelectedDrawing != null) {
            Log.e("Drawing", "a: Restoring temp drawing${drawingViewModel._tempSelectedDrawing}")
            binding.canvasView.restoreState(drawingViewModel._tempSelectedDrawing)
        }

        Log.e("Drawing", "b: Drawing Screen Created")
        return binding.root
    }

    private fun topBar() {
        // Go back button
        binding.goBackButton.setOnClickListener {
            Log.e("Drawing", "goBackButton Clicked")
            findNavController().navigate(R.id.select_drawing_action)
        }

        // Save button
        binding.saveButton.setOnClickListener {
            binding.drawingTitle.clearFocus()

            Log.e("Drawing", "saveButton Clicked")
            val currentBitmap = binding.canvasView.saveState()
            currentBitmap?.let {
                val pictureTitle = binding.drawingTitle.text.toString()
                lifecycleScope.launch {
                    if (!drawingViewModel.isSavetoSaveDrawing(pictureTitle, it)) {
                        Log.e(
                            "DrawingRepository",
                            "A drawing with the same name already exists. $pictureTitle"
                        )
                        Dialogs.showSaveWarningDialog(
                            this@DrawingScreen,
                            drawingViewModel,
                            pictureTitle,
                            it,
                            "A drawing with the same name already exists. Do you want to overwrite it?"
                        )
                    } else {
                        drawingViewModel.saveDrawing(pictureTitle, it)
                    }
                }
            }
        }

        // Drag button
        binding.dragButton.setOnClickListener {
            isDragEnabled = !isDragEnabled  // Toggle the state
            if (isDragEnabled) {
                binding.dragButton.text = "Drag Enabled"
                drawingViewModel.toggleDrag(true)
            } else {
                binding.dragButton.text = "Drag Disabled"
                drawingViewModel.toggleDrag(false)
            }
        }
    }

    /**
     * Observe the ViewModel LiveData
     * From viewModel, update the Canvas view
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

        drawingViewModel.penShape.observe(viewLifecycleOwner, Observer { shape ->
            binding.canvasView.setBrushShape(shape)
        })

        drawingViewModel.isDragEnabled.observe(viewLifecycleOwner, Observer { isDragEnabled ->
            Log.e("Drawing", "isDragEnabled in drawingscreen: $isDragEnabled")
            binding.canvasView.toggleDrag(isDragEnabled)
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

    private fun shapeBar() {
        binding.StrokeButton.setOnClickListener() {
            drawingViewModel.setPenShape("stroke")
        }
        binding.CircleButton.setOnClickListener() {
            drawingViewModel.setPenShape("circle")
        }
        binding.TriangleButton.setOnClickListener() {
            drawingViewModel.setPenShape("triangle")
        }
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
     * Save the current state of the canvas
     */
    fun saveCanvasState(): Bitmap? {
        return binding.canvasView.saveState()
    }

}
