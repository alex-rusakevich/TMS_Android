package com.example.tms_android.domain

data class ShoppingItem (
    val id: Long = System.currentTimeMillis(),
    val name: String,
    var isPurchased: Boolean = false
)