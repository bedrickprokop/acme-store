package com.acmestore.view.adapter

import android.graphics.Rect
import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView

class SpaceDecoration(
    @IntRange(from = 0) private val size: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val adapter = parent.adapter
        if (adapter != null) {
            val lastPosition = adapter.itemCount.minus(1)
            if (position < lastPosition) outRect.bottom = size
        }
    }
}