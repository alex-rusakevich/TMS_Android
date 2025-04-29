package com.example.tms_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PostsAdapter(
    private val onButtonClick: (PostItem.TextWithButtonPost) -> Unit
) : RecyclerView.Adapter<PostViewHolder>() {

    private var items: List<PostItem> = emptyList()

    fun updateItems(newItems: List<PostItem>) {
        val diffResult = DiffUtil.calculateDiff(PostDiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PostItem.AuthorPost -> VIEW_TYPE_AUTHOR
            is PostItem.ImagePost -> VIEW_TYPE_IMAGE
            is PostItem.TextWithButtonPost -> VIEW_TYPE_TEXT_WITH_BUTTON
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return when (viewType) {
            VIEW_TYPE_AUTHOR -> AuthorPostViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_author_post, parent, false)
            )
            VIEW_TYPE_IMAGE -> ImagePostViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_post, parent, false)
            )
            VIEW_TYPE_TEXT_WITH_BUTTON -> TextWithButtonPostViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_text_with_button_post, parent, false),
                onButtonClick
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    companion object {
        private const val VIEW_TYPE_AUTHOR = 0
        private const val VIEW_TYPE_IMAGE = 1
        private const val VIEW_TYPE_TEXT_WITH_BUTTON = 2
    }
}

class PostDiffCallback(
    private val oldList: List<PostItem>,
    private val newList: List<PostItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is PostItem.AuthorPost && newItem is PostItem.AuthorPost ->
                oldItem.id == newItem.id
            oldItem is PostItem.ImagePost && newItem is PostItem.ImagePost ->
                oldItem.id == newItem.id
            oldItem is PostItem.TextWithButtonPost && newItem is PostItem.TextWithButtonPost ->
                oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}