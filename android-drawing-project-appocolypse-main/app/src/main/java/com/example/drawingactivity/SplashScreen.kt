package com.example.drawingactivity

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.drawingactivity.databinding.FragmentSplashScreenBinding

class SplashScreen : Fragment() {
    lateinit var binding: FragmentSplashScreenBinding
    private val drawingViewModel: DrawingViewModel by activityViewModels()
    private var timeCallback: () -> Unit = {}

    override fun onCreateView(
        inflator: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        Log.e("Drawing", "Splash Screen Created")
        setTimer()

        return binding.root
    }

    /**
     * Set the listener for the button
     */
    public fun setListener(listener: () -> Unit) {
        timeCallback = listener
    }

    /**
     * Set the timer for the splash screen
     * Waits for 5 seconds before switching to MainScreen through a callback
     */
    public fun setTimer() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Not needed
                Log.e("Drawing", "Splash Screen Timer is running")
            }

            override fun onFinish() {
                // Switch to Main Screen
                Log.e("Drawing", "Splash Screen Timer is done, switch to Main Screen")
                timeCallback()
            }

        }.start()
    }
}