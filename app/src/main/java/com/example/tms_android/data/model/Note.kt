package com.example.tms_android.data.model

import java.util.Date

data class Note (
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val createdAt: Date = Date()
)
