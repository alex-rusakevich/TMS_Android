package com.example.tms_android.data.model

import java.util.Date

data class Note (
    val id: Long = System.currentTimeMillis(),
    var text: String,
    var createdAt: Date = Date()
)
