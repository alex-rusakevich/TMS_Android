package com.example.tms_android.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tms_android.R
import com.example.tms_android.presentation.adapters.ShoppingListAdapter
import com.example.tms_android.data.ShoppingRepository
import com.example.tms_android.databinding.ActivityMainBinding
import com.example.tms_android.domain.ShoppingItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShoppingListAdapter
    private val repositoryListener: (List<ShoppingItem>) -> Unit = { items ->
        adapter.updateItems(items)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        ShoppingRepository.addListener(repositoryListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        ShoppingRepository.removeListener(repositoryListener)
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.addButton.setOnClickListener {
            val itemName = binding.inputField.text.toString()

            if(itemName.isBlank()) {
                Toast.makeText(this, getString(R.string.enter_goods_name), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ShoppingRepository.addItem(itemName)
            binding.inputField.text.clear()
        }
    }
}