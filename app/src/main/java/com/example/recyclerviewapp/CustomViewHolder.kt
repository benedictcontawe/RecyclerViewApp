package com.example.recyclerviewapp

import android.view.MotionEvent
import android.view.View
import com.example.recyclerviewapp.databinding.CellItemBinder

class CustomViewHolder : BaseViewHolder, View.OnClickListener, View.OnTouchListener {

    companion object {
        private val TAG : String = CustomViewHolder::class.java.getSimpleName()
    }

    private val binder : CellItemBinder

    constructor(binder : CellItemBinder, customListeners : CustomListeners) : super(binder.getRoot(), customListeners) {
        this.binder = binder
    }

    override fun bindDataToViewHolder(model : CustomHolderModel, position : Int, swipeState : SwipeState) {
        binder.setHolder(model)
        binder.setPosition(position)
        binder.setState(swipeState)
        binder.executePendingBindings()
        //region Input Data
        binder.imageView.setBackgroundResource(model.icon)
        binder.textView.setText(model.name)
        //endregion
        //region Set Event Listener
        /* On Click */
        binder.buttonLeft.setOnClickListener(this@CustomViewHolder)
        binder.buttonRight.setOnClickListener(this@CustomViewHolder)
        binder.cardView.setOnClickListener(this@CustomViewHolder)
        /* On Touch Swipe */
        binder.cardView.setOnTouchListener(this@CustomViewHolder)
        //endregion
    }

    override fun onClick(view : View) {
        if (view == binder.buttonLeft) {
            getListener().onClickLeft(binder.getHolder(), binder.getPosition())
        } else if (view == binder.buttonRight) {
            getListener().onClickRight(binder.getHolder(), binder.getPosition())
        } else if (view == binder.cardView) {
            logDebug(TAG,"Card View ${binder.getHolder()} ${binder.getPosition()}")
        }
    }

    override fun onTouch(view : View, event : MotionEvent) : Boolean {
        return if (view == binder.cardView && event.getAction() == MotionEvent.ACTION_DOWN) {
            dXLead = view.getX() - event.getRawX()
            dXTrail = view.getRight() - event.getRawX()
            logDebug(TAG, "MotionEvent.ACTION_DOWN")
            false
        } else if (view == binder.cardView && event.getAction() == MotionEvent.ACTION_MOVE) {
            view.getParent().requestDisallowInterceptTouchEvent(true)
            binder.cardView.animate()
                .x(onSwipeMove(event.getRawX() + dXLead, event.getRawX() + dXTrail, binder.getState()))
                .setDuration(0)
                .start()
            logDebug(TAG, "MotionEvent.ACTION_MOVE")
            false
        } else if (view == binder.cardView && event.getAction() == MotionEvent.ACTION_UP) {
            binder.cardView.animate()
                .x(onSwipeUp())
                .setDuration(250)
                .start()
            logDebug(TAG, "MotionEvent.ACTION_UP")
            false
        } else true
    }
}