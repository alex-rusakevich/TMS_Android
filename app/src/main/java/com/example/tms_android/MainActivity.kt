package com.example.tms_android

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tms_android.data.ViewModel
import com.example.tms_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var progressBar: ProgressBar
    private lateinit var resultText: TextView
    private lateinit var loadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        progressBar = binding.progressBar
        resultText = binding.resultText
        loadButton = binding.loadButton

        loadButton.setOnClickListener {
            viewModel.loadData()
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is ViewModel.UiState.Idle -> {
                    progressBar.visibility = ProgressBar.GONE
                    resultText.text = "Нажмите кнопку для загрузки"
                }

                is ViewModel.UiState.Loading -> {
                    progressBar.visibility = ProgressBar.VISIBLE
                    resultText.text = "Загрузка..."
                }

                is ViewModel.UiState.Success -> {
                    progressBar.visibility = ProgressBar.GONE
                    resultText.text = state.data
                }

                is ViewModel.UiState.Error -> {
                    progressBar.visibility = ProgressBar.GONE
                    resultText.text = "Ошибка: ${state.message}"
                }
            }
        }
    }
}