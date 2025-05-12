package com.example.tms_android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.tms_android.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val countdownJob = lifecycleScope.launch {
            for (i in 10 downTo 0) {
                delay(1000)
                binding.countdown.text = i.toString()
            }
        }

        binding.stopCountdown.setOnClickListener {
            if(countdownJob.isActive){
                countdownJob.cancel()
                binding.countdown.text = getString(R.string.countdown_stopped)
            }
        }
    }
}