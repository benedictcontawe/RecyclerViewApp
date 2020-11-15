package com.example.recyclerviewapp;

import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static String TAG = CustomItemTouchHelperCallback.class.getSimpleName();
    private static final float ALPHA_FULL = 1.0f;
    private static final float ALPHA_BIT = 0.7f;
    private static final float ALPHA_HALF = 0.5f;
    private static final long ALPHA_DURATION = 200L;
    private static final int LINEAR_DRAG_FLAGS = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    private static final int LINEAR_SWIPE_FLAGS = ItemTouchHelper.START | ItemTouchHelper.END;
    private static final int GRID_SWIPE_FLAGS = ItemTouchHelper.ACTION_STATE_IDLE;
    private static final int GRID_DRAG_FLAGS = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    private boolean canDrag;
    private boolean canSwipe;
    private boolean withFadeOutSwipe;
    private boolean withTransparentDrag;

    private CustomItemTouchHelperListener customItemTouchHelperListener;

    public CustomItemTouchHelperCallback(CustomItemTouchHelperListener customItemTouchHelperListener, boolean canDrag, boolean canSwipe) {
        super();
        this.customItemTouchHelperListener = customItemTouchHelperListener;
        this.canDrag = canDrag;
        this.canSwipe = canSwipe;
        withFadeOutSwipe = false;
        withTransparentDrag = false;
    }

    public void setFadeOutSwipe(boolean withFadeOutSwipe) {
        this.withFadeOutSwipe = withFadeOutSwipe;
    }

    public void setTransparentDrag(boolean withTransparentDrag) {
        this.withTransparentDrag = withTransparentDrag;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return canSwipe;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            Log.d(TAG,"ItemTouchHelper.ACTION_STATE_IDLE");
        } else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            Log.d(TAG,"ItemTouchHelper.ACTION_STATE_SWIPE");
        } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && withTransparentDrag) {
            Log.d(TAG,"ItemTouchHelper.ACTION_STATE_DRAG");
            //viewHolder.itemView.setAlpha(ALPHA_HALF);
            viewHolder.itemView.animate().alpha(ALPHA_BIT).setDuration(ALPHA_DURATION);
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (canSwipe) {
            customItemTouchHelperListener.onItemSwiped(viewHolder.getAdapterPosition());
        } else {

        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (withFadeOutSwipe && actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            float alpha = (ALPHA_FULL - Math.abs(dX) / (viewHolder.itemView.getWidth())); // as Float
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if (canDrag && viewHolder.getItemViewType() == target.getItemViewType()) {
            customItemTouchHelperListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            return ItemTouchHelper.Callback.makeMovementFlags(GRID_DRAG_FLAGS, GRID_SWIPE_FLAGS);
        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            return ItemTouchHelper.Callback.makeMovementFlags(LINEAR_DRAG_FLAGS, LINEAR_SWIPE_FLAGS);
        } else {
            return ItemTouchHelper.Callback.makeMovementFlags(LINEAR_DRAG_FLAGS, LINEAR_SWIPE_FLAGS);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(ALPHA_FULL);
        //viewHolder.itemView.animate().alpha(ALPHA_FULL).setDuration(ALPHA_DURATION);
        customItemTouchHelperListener.onItemClear(viewHolder.getAdapterPosition());
    }
}