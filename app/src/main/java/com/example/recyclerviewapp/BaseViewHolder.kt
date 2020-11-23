package com.example.recyclerviewapp

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.Display
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    companion object {
        private val TAG : String = BaseViewHolder::class.java.getSimpleName()
    }
    /** Main */
    private lateinit var context : Context
    private var activity : Activity? = null
    private val customListeners : CustomListeners
    /** On Swipe */
    lateinit var size : Point
    lateinit var display : Display
    public var dX : Float = 0.0f
    public var cardViewStart : Float = 0.0f
    public var cardViewEnd : Float = 0.0f

    constructor(context : Context, itemView : View, activity : Activity?, customListeners : CustomListeners) : super(itemView) {
        this.activity = activity
        this.customListeners = customListeners
    }

    public fun swipeEdgeDetect(currentwidth : Float, swipeState : SwipeState) : Float {
        return when(swipeState) {
            SwipeState.SWIPE_NONE -> { /**For Swipe None Only */
                Log.e("SWIPE_NONE","${currentwidth}")
                cardViewStart
            }
            SwipeState.SWIPE_LEFT -> { /**For Swipe Left Only */
                Log.e("SWIPE_LEFT","${currentwidth}")
                if(currentwidth <= cardViewStart) {
                    currentwidth
                }
                else {
                    cardViewStart
                }
            }
            SwipeState.SWIPE_RIGHT -> { /**For Swipe Right Only */
                Log.e("SWIPE_RIGHT","${currentwidth}")
                if(currentwidth >= cardViewStart){
                    Log.e("swipeEdgeDetect","if")
                    currentwidth
                }
                else{
                    Log.e("swipeEdgeDetect","else")
                    cardViewStart
                }
            }
            SwipeState.SWIPE_LEFT_RIGHT -> { /**For Swipe Left And Right Only */
                Log.e("SWIPE_LEFT_RIGHT","${currentwidth}")
                currentwidth
            }
            else -> { /**For Unknown Swipe Only */
                Log.e("UNKNOWN","${currentwidth}")
                cardViewStart
            }
        }
    }

    open public fun bindDataToViewHolder(item : CustomViewModel, position : Int, swipeState : SwipeState) {
        if (activity != null) {
            bindSwipeToViewHolder(swipeState)
        }
    }

    open public fun bindSwipeToViewHolder(swipeState : SwipeState) { /* On Swipe */
        size = Point()
        display = activity?.getWindowManager()?.getDefaultDisplay()!!
        display.getSize(size)
        var dX : Float = 0.toFloat()
        cardViewStart = size.x.toFloat() * 0.10f
        cardViewEnd = size.x.toFloat() * 0.90f
    }
}