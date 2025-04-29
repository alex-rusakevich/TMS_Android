package com.example.tms_android

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey

abstract class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(postItem: PostItem)
}

class AuthorPostViewHolder(view: View) : PostViewHolder(view) {
    private val authorName: TextView = view.findViewById(R.id.author_name)
    private val postText: TextView = view.findViewById(R.id.post_text)

    override fun bind(postItem: PostItem) {
        postItem as PostItem.AuthorPost
        authorName.text = postItem.authorName
        postText.text = postItem.text
    }
}

class ImagePostViewHolder(view: View) : PostViewHolder(view) {
    private val postImage: ImageView = view.findViewById(R.id.post_image)
    private val postText: TextView = view.findViewById(R.id.post_text)

    override fun bind(postItem: PostItem) {
        postItem as PostItem.ImagePost
        postText.text = postItem.text

        Glide.with(itemView.context)
            .load(postItem.imageUrl)
            .signature(ObjectKey(System.currentTimeMillis().toString()))
            .into(postImage)
    }
}

class TextWithButtonPostViewHolder(
    view: View,
    private val onButtonClick: (PostItem.TextWithButtonPost) -> Unit
) : PostViewHolder(view) {
    private val postText: TextView = view.findViewById(R.id.post_text)
    private val actionButton: Button = view.findViewById(R.id.action_button)

    override fun bind(postItem: PostItem) {
        postItem as PostItem.TextWithButtonPost
        postText.text = postItem.text
        actionButton.text = postItem.buttonText
        actionButton.setOnClickListener { onButtonClick(postItem) }
    }
}