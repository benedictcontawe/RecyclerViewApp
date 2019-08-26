package com.example.recyclerviewapp;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomBottomOffsetDecoration extends RecyclerView.ItemDecoration {

    private int bottomItemOffset;

    public CustomBottomOffsetDecoration(int bottomItemOffset) {
        this.bottomItemOffset = bottomItemOffset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);

        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0, 0, 0, bottomItemOffset);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}
