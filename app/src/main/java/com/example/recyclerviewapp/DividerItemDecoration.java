package com.example.recyclerviewapp;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private final int dividerHeight;
    private final Drawable dividerDrawable;

    public DividerItemDecoration(int dividerHeight, Drawable dividerDrawable) {
        this.dividerHeight = dividerHeight;
        this.dividerDrawable = dividerDrawable;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int top = child.getTop() + child.getHeight();
            int bottom = top + dividerHeight;
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(canvas);
        }
    }
}