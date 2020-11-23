package com.example.recyclerviewapp

import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    companion object {
        private val TAG : String = BaseViewHolder::class.java.getSimpleName()
    }
    /** Main */
    private lateinit var context : Context
    private val customListeners : CustomListeners
    /** On Swipe */
    val size : Point = Point()
    private val display : Display
    private val windowManager : WindowManager
    public var dX : Float = 0.toFloat()
    public var cardViewLeading : Float = 0.0f
    public var cardViewLeadEdge : Float = 0.0f
    public var cardViewTrailEdge : Float = 0.0f
    public var cardViewTrailing : Float = 0.0f

    constructor(context : Context, itemView : View, customListeners : CustomListeners) : super(itemView) {
        this.context = context
        this.customListeners = customListeners
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        display = windowManager.getDefaultDisplay() //activity.getWindowManager().getDefaultDisplay()
    }

    public fun swipeEdgeDetect(currentwidth : Float, swipeState : SwipeState) : Float {
        return when(swipeState) {
            SwipeState.SWIPE_NONE -> { /**For Swipe None Only */
                Log.e("SWIPE_NONE","${currentwidth}")
                cardViewLeading
            }
            SwipeState.SWIPE_LEFT -> { /**For Swipe Left Only */
                Log.e("SWIPE_LEFT","${currentwidth}")
                if(currentwidth <= cardViewLeading) {
                    currentwidth
                }
                else {
                    cardViewLeading
                }
            }
            SwipeState.SWIPE_RIGHT -> { /**For Swipe Right Only */
                Log.e("SWIPE_RIGHT","${currentwidth}")
                if(currentwidth >= cardViewLeading){
                    Log.e("swipeEdgeDetect","if")
                    currentwidth
                }
                else{
                    Log.e("swipeEdgeDetect","else")
                    cardViewLeading
                }
            }
            SwipeState.SWIPE_LEFT_RIGHT -> { /**For Swipe Left And Right Only */
                Log.e("SWIPE_LEFT_RIGHT","${currentwidth}")
                currentwidth
            }
            else -> { /**For Unknown Swipe Only */
                Log.e("UNKNOWN","${currentwidth}")
                cardViewLeading
            }
        }
    }

    open public fun bindDataToViewHolder(item : CustomViewModel, position : Int, swipeState : SwipeState) {
        bindSwipeToViewHolder(swipeState)
    }

    open public fun bindSwipeToViewHolder(swipeState : SwipeState) { /* On Swipe */
        display.getSize(size)
        cardViewLeading = size.x.toFloat() * 0.10f
        cardViewLeadEdge = size.x.toFloat() * 0.25f
        cardViewTrailEdge = size.x.toFloat() * 0.75f
        cardViewTrailing = size.x.toFloat() * 0.90f
    }
}