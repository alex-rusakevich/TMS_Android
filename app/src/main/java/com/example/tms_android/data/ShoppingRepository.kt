package com.example.tms_android.data

import com.example.tms_android.domain.ShoppingItem

object ShoppingRepository {
    private val _items = mutableListOf<ShoppingItem>()
    private val listeners = mutableSetOf<(List<ShoppingItem>) -> Unit>()

    private val items: List<ShoppingItem>
        get() = _items.toList()

    fun addItem(name: String) {
        if (name.isNotBlank()) {
            _items.add(ShoppingItem(name = name))
            notifyListeners()
        }
    }

    fun togglePurchased(itemId: Long) {
        _items.find { it.id == itemId }?.let { item ->
            item.isPurchased = !item.isPurchased
            notifyListeners()
        }
    }

    fun addListener(listener: (List<ShoppingItem>) -> Unit) {
        listeners.add(listener)
        listener(items)
    }

    fun removeListener(listener: (List<ShoppingItem>) -> Unit) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        listeners.forEach { it(items) }
    }
}