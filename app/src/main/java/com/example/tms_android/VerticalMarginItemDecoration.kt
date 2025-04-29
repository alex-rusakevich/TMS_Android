package com.example.tms_android

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalMarginItemDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        with(outRect) {
            when (position) {
                0 -> {
                    top = 0
                    bottom = margin / 2
                }
                itemCount - 1 -> {
                    top = margin / 2
                    bottom = 0
                }
                else -> {
                    top = margin / 2
                    bottom = margin / 2
                }
            }
        }
    }
}