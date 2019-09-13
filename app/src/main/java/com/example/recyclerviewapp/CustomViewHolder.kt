package com.example.recyclerviewapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import android.widget.TextView

class CustomViewHolder : RecyclerView.ViewHolder {

    /**Main */
    private lateinit var context: Context
    private lateinit var customListeners: CustomListeners
    /**Data */
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    /**With Events and Others */
    private lateinit var cardView: CardView

    constructor(context : Context, itemView : View, customListeners : CustomListeners) : super(itemView){
        this.context = context
        this.customListeners = customListeners
    }

    init {
        imageView = itemView.findViewById(R.id.image_view)
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
    }

    private fun swipeEdgeDetect(currentwidth : Float, fullWidth : Float) : Float {
        Log.e("swipeEdgeDetect","${currentwidth}")
        //fullWidth * 0.05f
        //fullWidth * 0.95f

        /**For Swipe Right Only */
        /*
        return if(currentwidth <= (fullWidth * 0.05f)){
            fullWidth * 0.05f
        }
        else{
            currentwidth
        }
        */

        /**For Swipe Left Only */
        return if(currentwidth <= (fullWidth * 0.05f)){
            currentwidth
        }
        else{
            fullWidth * 0.05f
        }
    }

    public fun bindDataToViewHolder(item : CustomViewModel, position : Int, activity : Activity? = null) {
        //region Input Data
        imageView.setBackgroundResource(item.icon?:0)
        textView.setText(item.name)
        //endregion
        //region Set Listener
        cardView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

            }
        })
        /* On Swipe */
        if(activity != null) {
            val size : Point = Point()
            val display : Display = activity.getWindowManager().getDefaultDisplay()
            display.getSize(size)
            var dX : Float = 0.toFloat()
            val cardViewStart = size.x.toFloat() * 0.10f
            val cardViewEnd = size.x.toFloat() * 0.90f
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
                                    .x(event.getRawX() + dX)
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
        //endregion
    }
}