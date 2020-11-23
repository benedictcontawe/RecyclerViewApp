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

        public fun LogDebug(TAG : String, message : String) {
            Log.d(TAG,message)
        }

        public fun LogError(TAG : String, message : String) {
            Log.e(TAG,message)
        }
    }
    /** Main */
    private val context : Context
    private val customListeners : CustomListeners
    /** On Swipe */
    private val size : Point
    private val display : Display
    private val windowManager : WindowManager
    private val cardViewLeading : Float
    private val cardViewLeadEdge : Float
    private val cardViewTrailEdge : Float
    private val cardViewTrailing : Float
    public var dXLead : Float = 0.toFloat()
    public var dXTrail : Float = 0.toFloat()

    constructor(context : Context, itemView : View, customListeners : CustomListeners) : super(itemView) {
        this.context = context
        this.customListeners = customListeners
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        size = Point()
        display = windowManager.getDefaultDisplay() //activity.getWindowManager().getDefaultDisplay()
        display.getSize(size)
        cardViewLeading = size.x.toFloat() * 0.10f
        cardViewLeadEdge = size.x.toFloat() * 0.25f
        cardViewTrailEdge = size.x.toFloat() * 0.75f
        cardViewTrailing = size.x.toFloat() * 0.90f
    }

    public fun onSwipeMove(currentLead : Float, currentTrail : Float, swipeState : SwipeState) : Float {
        LogDebug(TAG,"onSwipeMove($currentLead Lead $cardViewLeading $cardViewLeadEdge - $cardViewTrailEdge $cardViewTrailing Trail $currentTrail)")
        return when {
            swipeState == SwipeState.NONE -> { /**For Swipe None Only */
                LogDebug(TAG,"SwipeState.NONE")
                cardViewLeading
            }
            swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {
                LogDebug(TAG,"Swipe Left Edge")
                currentLead
            }
            swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailing -> {
                LogDebug(TAG,"Swipe Left")
                currentLead
            }
            swipeState == SwipeState.RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                LogDebug(TAG,"Swipe Right Edge")
                currentLead
            }
            swipeState == SwipeState.RIGHT && currentLead > cardViewLeading && currentTrail > cardViewTrailing -> {
                LogDebug(TAG,"Swipe Right")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {
                LogDebug(TAG,"Swipe Left Edge")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                LogDebug(TAG,"Swipe Right Edge")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailing -> {
                LogDebug(TAG,"Swipe Left")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeading && currentTrail > cardViewTrailing -> {
                LogDebug(TAG,"Swipe Right")
                currentLead
            }
            else -> { /**For Unknown Swipe Only */
                LogDebug(TAG,"Unknown Swipe ${swipeState}")
                cardViewLeading
            }
        }
    }

    public fun onSwipeUp() : Float {
        LogDebug(TAG,"onSwipeUp()")
        return cardViewLeading
    }

    abstract fun bindDataToViewHolder(item : CustomViewModel, position : Int, swipeState : SwipeState)
}