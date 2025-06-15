package com.example.tms_android

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tms_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var progressDirection: ProgressDirection = ProgressDirection.FROM_0_TO_100

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

        binding.progressView.setOnClickListener {
            if(progressDirection == ProgressDirection.FROM_0_TO_100) {
                binding.progressView.setProgress(100f, animate = true)
                progressDirection = ProgressDirection.FROM_100_TO_0
            } else {
                binding.progressView.setProgress(0f, animate = true)
                progressDirection = ProgressDirection.FROM_0_TO_100
            }
        }
    }
}