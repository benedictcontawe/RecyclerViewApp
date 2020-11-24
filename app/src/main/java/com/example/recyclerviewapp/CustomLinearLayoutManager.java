package com.example.recyclerviewapp;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLinearLayoutManager extends LinearLayoutManager {

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        //super.smoothScrollToPosition(recyclerView, state, position);
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            private static final float MILLISECONDS_PER_INCH = 500f;
            private static final float DISTANCE_IN_PIXELS = 500f;
            private static final float DURATION = 500f;

            @Override
            protected int getHorizontalSnapPreference() {
                return super.getHorizontalSnapPreference();
            }

            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return CustomLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected int calculateTimeForScrolling(int dx) {
                float proportion = dx / DISTANCE_IN_PIXELS;
                return (int) (DURATION * proportion);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                //return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                return super.calculateSpeedPerPixel(displayMetrics);
            }
        };

        linearSmoothScroller.computeScrollVectorForPosition(position);
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}