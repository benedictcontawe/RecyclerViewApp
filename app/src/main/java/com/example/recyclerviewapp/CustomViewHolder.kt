package com.example.recyclerviewapp

import android.app.Activity
import android.content.Context
import android.util.Log
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
    private val cardView : CardView

    constructor(context : Context, itemView : View, activity : Activity?,customListeners : CustomListeners) : super(context, itemView, activity, customListeners) {

    }

    init {
        imageView = itemView.findViewById(R.id.image_view)
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
    }

    override fun bindDataToViewHolder(item : CustomViewModel, position : Int, swipeState : SwipeState) {
        //region Input Data
        imageView.setBackgroundResource(item.icon?:0)
        textView.setText(item.name)
        //endregion
        //region Set Listener
        /* On Click */
        cardView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view : View?) {
                //Do not remove this need this click listener to swipe with on touch listener
            }
        })
        super.bindDataToViewHolder(item, position, swipeState)
        //endregion
    }

    override fun bindSwipeToViewHolder(swipeState : SwipeState) {
        super.bindSwipeToViewHolder(swipeState)
        cardView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View, event: MotionEvent): Boolean {
                when (event.getAction()) {
                    MotionEvent.ACTION_DOWN -> {
                        dX = view.getX() - event.getRawX()
                        Log.e("ACTION_DOWN", "${dX}")
                        return false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        view.getParent().requestDisallowInterceptTouchEvent(true)
                        cardView.animate()
                                //.x(event.getRawX() + dX)
                                .x(swipeEdgeDetect(event.getRawX() + dX, swipeState))
                                .setDuration(0)
                                .start()
                        Log.e("ACTION_MOVE", "${event.getRawX()}")
                        return false
                    }
                    MotionEvent.ACTION_UP -> {
                        cardView.animate()
                                .x(cardViewStart)
                                .setDuration(250)
                                .start()
                        Log.e("ACTION_UP", "${(size.x.toFloat() * 0.05f)} ${(size.x.toFloat() * 0.95f)}")
                        return false
                    }
                }
                return true
            }
        })
    }
}