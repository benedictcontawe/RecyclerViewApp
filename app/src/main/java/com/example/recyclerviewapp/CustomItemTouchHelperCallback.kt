package com.example.recyclerviewapp

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class CustomItemTouchHelperCallback : ItemTouchHelper.Callback {

    companion object {
        private var TAG : String = CustomItemTouchHelperCallback::class.java.simpleName
        private const val ALPHA_FULL = 1.0f
        private const val ALPHA_BIT = 0.7f
        private const val ALPHA_HALF = 0.5f
        private const val ALPHA_DURATION = 200L
        private const val LINEAR_DRAG_FLAGS : Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        private const val LINEAR_SWIPE_FLAGS : Int = ItemTouchHelper.START or ItemTouchHelper.END
        private const val GRID_SWIPE_FLAGS : Int = ItemTouchHelper.ACTION_STATE_IDLE
        private const val GRID_DRAG_FLAGS : Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    }

    private var canDrag by Delegates.notNull<Boolean>()
    private var canSwipe by Delegates.notNull<Boolean>()
    private var withFadeOutSwipe by Delegates.notNull<Boolean>()
    private var withTransparentDrag by Delegates.notNull<Boolean>()

    private val customItemTouchHelperListener : CustomItemTouchHelperListener

    constructor(customItemTouchHelperListener : CustomItemTouchHelperListener, canDrag : Boolean, canSwipe : Boolean) : super() {
        this.customItemTouchHelperListener = customItemTouchHelperListener
        this.canDrag = canDrag
        this.canSwipe = canSwipe
        withFadeOutSwipe = false
        withTransparentDrag = false
    }

    public fun setFadeOutSwipe(withFadeOutSwipe : Boolean) {
        this.withFadeOutSwipe = withFadeOutSwipe
    }

    public fun setTransparentDrag(withTransparentDrag : Boolean) {
        this.withTransparentDrag = withTransparentDrag
    }

    override fun isLongPressDragEnabled() : Boolean {
        //return super.isLongPressDragEnabled()
        return false
    }

    override fun isItemViewSwipeEnabled() : Boolean {
        //return super.isItemViewSwipeEnabled()
        return canSwipe
    }

    override fun onSelectedChanged(viewHolder : RecyclerView.ViewHolder?, actionState : Int) {
        super.onSelectedChanged(viewHolder, actionState)
        when {
            actionState == ItemTouchHelper.ACTION_STATE_IDLE -> {
                Log.d(TAG,"ItemTouchHelper.ACTION_STATE_IDLE")
            }
            actionState == ItemTouchHelper.ACTION_STATE_SWIPE -> {
                Log.d(TAG,"ItemTouchHelper.ACTION_STATE_SWIPE")
            }
            actionState == ItemTouchHelper.ACTION_STATE_DRAG && withTransparentDrag -> {
                Log.d(TAG,"ItemTouchHelper.ACTION_STATE_DRAG")
                //viewHolder?.itemView?.setAlpha(ALPHA_HALF)
                viewHolder?.itemView?.animate()?.alpha(ALPHA_BIT)?.duration = ALPHA_DURATION
            }
            actionState == ItemTouchHelper.ACTION_STATE_DRAG && !withTransparentDrag -> {
                Log.d(TAG,"ItemTouchHelper.ACTION_STATE_DRAG")
            }
        }
    }

    override fun onSwiped(viewHolder : RecyclerView.ViewHolder, direction : Int) {
        when {
            canSwipe -> {
                customItemTouchHelperListener.onItemSwiped(viewHolder.getAdapterPosition())
            }
            else -> {

            }
        }
    }

    override fun onChildDraw(canvas : Canvas, recyclerView : RecyclerView, viewHolder : RecyclerView.ViewHolder, dX : Float, dY : Float, actionState : Int, isCurrentlyActive : Boolean) {
        when {
            withFadeOutSwipe && actionState === ItemTouchHelper.ACTION_STATE_SWIPE -> {
                // Fade out the view as it is swiped out of the parent's bounds
                val alpha = ALPHA_FULL - Math.abs(dX) / (viewHolder.itemView.width?:0) // as Float
                viewHolder.itemView.setAlpha(alpha)
                viewHolder.itemView.setTranslationX(dX)
            }
            else -> {
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
    }

    override fun onMove(recyclerView : RecyclerView, viewHolder : RecyclerView.ViewHolder, target : RecyclerView.ViewHolder) : Boolean {
        return when {
            canDrag && viewHolder.getItemViewType() == target.itemViewType -> {
                customItemTouchHelperListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition())
                true
            }
            else -> {
                false
            }
        }
    }

    override fun getMovementFlags(recyclerView : RecyclerView, viewHolder : RecyclerView.ViewHolder) : Int {
        return when {
            recyclerView.layoutManager is GridLayoutManager -> {
                ItemTouchHelper.Callback.makeMovementFlags(GRID_DRAG_FLAGS, GRID_SWIPE_FLAGS)
            }
            recyclerView.layoutManager is LinearLayoutManager -> {
                ItemTouchHelper.Callback.makeMovementFlags(LINEAR_DRAG_FLAGS, LINEAR_SWIPE_FLAGS)
            }
            else -> {
                ItemTouchHelper.Callback.makeMovementFlags(LINEAR_DRAG_FLAGS, LINEAR_SWIPE_FLAGS)
            }
        }
    }

    override fun clearView(recyclerView : RecyclerView, viewHolder : RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.setAlpha(ALPHA_FULL)
        //viewHolder.itemView.animate().alpha(ALPHA_FULL).duration = ALPHA_DURATION
        customItemTouchHelperListener.onItemClear(viewHolder.getAdapterPosition())
    }
}