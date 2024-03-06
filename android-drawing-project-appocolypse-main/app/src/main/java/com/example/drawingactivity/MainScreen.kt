package com.example.drawingactivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drawingactivity.databinding.FragmentMainScreenBinding

class MainScreen : Fragment() {
    lateinit var binding: FragmentMainScreenBinding
    var clickCallback: () -> Unit = {}
    val drawingViewModel: DrawingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflator: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(layoutInflater)

        // Switch to Drawing Screen when button is clicked
        binding.goDrawingScreen.setOnClickListener {
            clickCallback()
        }

        val adapter = PictureAdapter(listOf(),
            // Switch to Drawing Screen by selecting which drawing to go to
            onDrawingSelected = {
                Log.e("Drawing", "Selected $it")

                // TODO: Update the canvas here
                drawingViewModel.setSelectDrawing(it)
                clickCallback()
            })

        // Set up the recycler view
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        drawingViewModel.viewList.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
        }

        Log.e("Drawing", "Main Screen Created")
        return binding.root
    }

    /**
     * Set the listener for the button
     */
    public fun setListener(listener: () -> Unit) {
        clickCallback = listener
    }
}