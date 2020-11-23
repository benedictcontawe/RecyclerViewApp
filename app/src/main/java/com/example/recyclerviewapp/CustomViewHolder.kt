package com.example.recyclerviewapp

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class CustomViewHolder : BaseViewHolder {

    companion object {
        private val TAG : String = CustomViewHolder::class.java.getSimpleName()
    }
    /**Data */
    private val imageView : ImageView
    private val textView : TextView
    /**With Events and Others */
    private val leftImage : ImageView
    private val rightImage : ImageView
    private val cardView : CardView

    constructor(context : Context, itemView : View,customListeners : CustomListeners) : super(context, itemView, customListeners) {
        imageView = itemView.findViewById(R.id.image_view)
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
        leftImage = itemView.findViewById(R.id.button_left)
        rightImage = itemView.findViewById(R.id.button_right)
    }

    override fun bindDataToViewHolder(item : CustomViewModel, position : Int, swipeState : SwipeState) {
        //region Input Data
        imageView.setBackgroundResource(item.icon?:0)
        textView.setText(item.name)
        //endregion
        //region Set Event Listener
        /* On Click */
        leftImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?) {
                getListener().onClickLeft(item, position)
            }
        })
        rightImage.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?) {
                getListener().onClickRight(item, position)
            }
        })
        cardView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?) {
                //Do not remove this need this click listener to swipe with on touch listener
            }
        })
        /* On Touch Swipe */
        cardView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view : View, event : MotionEvent) : Boolean {
                return when (event.getAction()) {
                    MotionEvent.ACTION_DOWN -> {
                        dXLead = view.getX() - event.getRawX()
                        dXTrail = view.getRight() - event.getRawX()
                        LogDebug(TAG, "MotionEvent.ACTION_DOWN")
                        false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        view.getParent().requestDisallowInterceptTouchEvent(true)
                        view.animate()
                                .x(onSwipeMove(event.getRawX() + dXLead, event.getRawX() + dXTrail,swipeState))
                                .setDuration(0)
                                .start()
                        item.state = getSwipeState(event.getRawX() + dXLead, event.getRawX() + dXTrail, swipeState)
                        LogDebug(TAG, "MotionEvent.ACTION_MOVE")
                        false
                    }
                    MotionEvent.ACTION_UP -> {
                        view.animate()
                                .x(onSwipeUp(item.state))
                                .setDuration(250)
                                .start()
                        LogDebug(TAG, "MotionEvent.ACTION_UP")
                        false
                    }
                    else -> true
                }
            }
        })
        //endregion
    }
}