package com.example.tms_android

import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tms_android.databinding.ActivityMainBinding

/*
* Использовать simple_list_item_2 в ListView

Отображать заголовок и подзаголовок в каждой строке(использовать SimpleAdapter)
* */
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

        val data: List<Map<String, String>> = List(100) {
            mapOf("title" to "Title $it", "subtitle" to "Sub-title $it")
        }

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf("title", "subtitle"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.listViewOne.adapter = adapter
    }
}