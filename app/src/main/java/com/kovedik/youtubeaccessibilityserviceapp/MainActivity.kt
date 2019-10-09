package com.kovedik.youtubeaccessibilityserviceapp

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extraCode = intent.getIntExtra(YoutubeAccessibilityService.EXTRA_BUTTON_CLICKED, 0)
        if (extraCode == 1) {
            Log.d(TAG, "App launched after Youtube buttons clicked")
        } else {
            Toast.makeText(this, "Please enable YoutubeAccessibilityService", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            finish()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
