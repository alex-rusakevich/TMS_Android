package com.example.tms_android

import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tms_android.databinding.ActivityMainBinding

/*
* Использовать simple_list_item_2 в ListView

Отображать заголовок и подзаголовок в каждой строке(использовать SimpleAdapter)
* */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemsAdapter
    private var counter = 0

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

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemsAdapter(mutableListOf())
        recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            counter++
            val newItem = Item(counter, getString(R.string.element_no, counter))
            adapter.addItem(newItem)

            recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }
}