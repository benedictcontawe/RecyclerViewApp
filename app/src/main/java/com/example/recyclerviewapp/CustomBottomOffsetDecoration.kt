package com.example.recyclerviewapp

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomBottomOffsetDecoration : RecyclerView.ItemDecoration {

    private var bottomItemOffset : Int? = null

    constructor(bottomItemOffset : Int) {
        this.bottomItemOffset = bottomItemOffset
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val dataSize = state.getItemCount()
        val position = parent.getChildAdapterPosition(view)
        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0, 0, 0, bottomItemOffset?:0)
        } else {
            outRect.set(0, 0, 0, 0)
        }

    }

}