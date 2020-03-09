package com.igorganapolsky.vibratingwatchapp.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.igorganapolsky.vibratingwatchapp.R

/**
 * Main entry activity which opens the [androidx.navigation.NavGraph] to navigate to home screen.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
