package com.example.recyclerviewapp

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class CustomLinearLayoutManager : LinearLayoutManager {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}

    companion object {
        private val SMOOTHNESS_DISTANCE_IN_PIXELS = 500f
        private val SMOOTHNESS_DURATION = 500f
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@CustomLinearLayoutManager
                        .computeScrollVectorForPosition(targetPosition)
            }

            override fun calculateTimeForScrolling(dx: Int): Int {
                val proportion = dx.toFloat() / SMOOTHNESS_DISTANCE_IN_PIXELS
                return (SMOOTHNESS_DURATION * proportion).toInt()
            }
        }
        linearSmoothScroller.computeScrollVectorForPosition(position)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}