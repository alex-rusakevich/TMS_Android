package com.example.tms_android

sealed class PostItem {
    data class AuthorPost(
        val id: Int,
        val authorName: String,
        val text: String
    ) : PostItem()

    data class ImagePost(
        val id: Int,
        val imageUrl: String,
        val text: String
    ) : PostItem()

    data class TextWithButtonPost(
        val id: Int,
        val text: String,
        val buttonText: String
    ) : PostItem()
}