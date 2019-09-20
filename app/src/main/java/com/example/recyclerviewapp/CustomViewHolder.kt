package com.example.recyclerviewapp

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

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

    private fun swipeEdgeDetect(currentwidth : Float, leftEdgeWidth : Float, rightEdgeWidth : Float, swipeState : Int) : Float {
        return when(swipeState) {
            CustomViewModel.SWIPE_NONE -> {
                /**For Swipe None Only */
                Log.e("SWIPE_NONE","${currentwidth}")
                leftEdgeWidth
            }
            CustomViewModel.SWIPE_LEFT -> {
                /**For Swipe Left Only */
                Log.e("SWIPE_LEFT","${currentwidth}")
                if(currentwidth <= leftEdgeWidth){
                    currentwidth
                }
                else{
                    leftEdgeWidth
                }
            }
            CustomViewModel.SWIPE_RIGHT -> {
                /**For Swipe Right Only */
                Log.e("SWIPE_RIGHT","${currentwidth}")
                if(currentwidth >= leftEdgeWidth){
                    Log.e("swipeEdgeDetect","if")
                    currentwidth
                }
                else{
                    Log.e("swipeEdgeDetect","else")
                    leftEdgeWidth
                }
            }
            CustomViewModel.SWIPE_LEFT_RIGHT -> {
                /**For Swipe Left And Right Only */
                Log.e("SWIPE_LEFT_RIGHT","${currentwidth}")
                currentwidth
            }
            else -> {
                Log.e("UNKNOWN","${currentwidth}")
                /**For Unknown Swipe Only */
                leftEdgeWidth
            }
        }
    }

    public fun bindDataToViewHolder(item : CustomViewModel, position : Int, activity : Activity? = null, swipeState : Int) {
        //region Input Data
        imageView.setBackgroundResource(item.icon?:0)
        textView.setText(item.name)
        //endregion
        //region Set Listener
        /* On Click */
        cardView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //Do not remove this need this click listener to swipe with on touch listener
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
                                    //.x(event.getRawX() + dX)
                                    .x(swipeEdgeDetect(event.getRawX() + dX, cardViewStart, cardViewEnd, swipeState))
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