package com.example.drawingactivity

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.drawingactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val drawingViewModel: DrawingViewModel by viewModels()
    lateinit var fragSplashScreen: SplashScreen
    lateinit var fragMainScreen: MainScreen
    lateinit var fragDrawingScreen: DrawingScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        if (!this::fragSplashScreen.isInitialized) {
            fragSplashScreen = SplashScreen()
        }
        if (!this::fragMainScreen.isInitialized) {
            fragMainScreen = MainScreen()
        }
        if (!this::fragDrawingScreen.isInitialized) {
            fragDrawingScreen = DrawingScreen()
        }

        if (savedInstanceState == null) {
            // Only add the initial fragment if it's a new creation, not on configuration change
            val fragTrans = supportFragmentManager.beginTransaction()
            fragTrans.replace(R.id.fragment_container, fragSplashScreen)
            fragTrans.commit()
        }

        // Observe changes in the current fragment LiveData
        drawingViewModel.currentFragment.observe(this) { fragment ->
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitNow()
            }
        }

        // Switches from SplashScreen to MainScreen
        fragSplashScreen.setListener() {
            if (!this::fragMainScreen.isInitialized) {
                fragMainScreen = MainScreen()
            }

            drawingViewModel.setCurrentFragment(fragMainScreen)
        }

        // Switches from MainScreen to DrawingScreen
        fragMainScreen.setListener {
            if (!this::fragDrawingScreen.isInitialized) {
                fragDrawingScreen = DrawingScreen()
            }

            drawingViewModel.setCurrentFragment(fragDrawingScreen)
        }

        // Switches from DrawingScreen to MainScreen
        fragDrawingScreen.setListener {
            if (!this::fragMainScreen.isInitialized) {
                fragMainScreen = MainScreen()
            }

            drawingViewModel.setCurrentFragment(fragMainScreen)
        }

        if (savedInstanceState != null) {
            val savedBitmap = savedInstanceState.getParcelable<Bitmap>("canvasState")
            drawingViewModel.setUnsaveOnRotation(savedBitmap)
        }

        setContentView(binding.root)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Check if fragDrawingScreen is initialized and is currently added to the activity
        if (this::fragDrawingScreen.isInitialized && fragDrawingScreen.isAdded) {
            // Save the state of the CanvasView
            val currentBitmap = fragDrawingScreen.saveCanvasState()
            outState.putParcelable("canvasState", currentBitmap)
        }
    }


}