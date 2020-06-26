package com.example.recyclerviewapp

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class CustomItemTouchHelperCallback : ItemTouchHelper.Callback {

    companion object {
        private var TAG : String = CustomItemTouchHelperCallback::class.java.simpleName
        private const val DRAG_FLAGS : Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        private const val SWIPE_FLAGS : Int = ItemTouchHelper.START or ItemTouchHelper.END
    }
    private val customItemTouchHelperListener : CustomItemTouchHelperListener

    constructor(customItemTouchHelperListener : CustomItemTouchHelperListener) : super() {
        this.customItemTouchHelperListener = customItemTouchHelperListener
    }

    override fun isLongPressDragEnabled(): Boolean {
        //return super.isLongPressDragEnabled()
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        //return super.isItemViewSwipeEnabled()\
        return true
    }

    override fun clearView(recyclerView : RecyclerView, viewHolder : RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        customItemTouchHelperListener.onItemDismiss(viewHolder.getAdapterPosition())
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {

        }
    }

    override fun getMovementFlags(recyclerView : RecyclerView, viewHolder : RecyclerView.ViewHolder) : Int {
        return makeMovementFlags(DRAG_FLAGS, SWIPE_FLAGS)
    }

    override fun onMove(recyclerView : RecyclerView, viewHolder : RecyclerView.ViewHolder, target : RecyclerView.ViewHolder) : Boolean {
        customItemTouchHelperListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition())
        return true
    }

    override fun onSwiped(viewHolder : RecyclerView.ViewHolder, direction : Int) {
        customItemTouchHelperListener.onItemSwiped(viewHolder.getAdapterPosition())
    }
}