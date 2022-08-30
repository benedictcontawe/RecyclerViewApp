package com.example.recyclerviewapp

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    companion object {
        private val TAG : String = BaseViewHolder::class.java.getSimpleName()
    }
    /** Main */
    private val customListeners : CustomListeners
    /** On Swipe */
    private val windowManager : WindowManager
    private val cardViewLeading : Float
    private val cardViewLeadEdge : Float
    private val cardViewTrailEdge : Float
    private val cardViewTrailing : Float
    private val width : Int
    protected var dXLead : Float = 0.toFloat()
    protected var dXTrail : Float = 0.toFloat()

    constructor(itemView : View, customListeners : CustomListeners) : super(itemView) {
        this.customListeners = customListeners
        windowManager = itemView.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        width = getWidth()
        cardViewLeading = width.toFloat() * 0.10f //leading
        cardViewLeadEdge = width.toFloat() * 0.25f //leading_rubber
        cardViewTrailEdge = width.toFloat() * 0.75f //trailing_rubber
        cardViewTrailing = width.toFloat() * 0.90f //trailing
    }

    protected fun logDebug(TAG : String, message : String) {
        Log.d(TAG,message)
    }

    protected fun logError(TAG : String, message : String) {
        Log.e(TAG,message)
    }

    protected fun getListener() : CustomListeners {
        return customListeners
    }

    private fun getWidth() : Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.getCurrentWindowMetrics()
            val windowInsets: WindowInsets = windowMetrics.getWindowInsets()
            val insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout())
            val insetsWidth = insets.right + insets.left
            //val insetsHeight = insets.top + insets.bottom
            val bounds = windowMetrics.getBounds()
            //val height  : Int = bounds.height() - insetsHeight
            bounds.width() - insetsWidth //val width : Int = bounds.width() - insetsWidth
        } else {
            val size : Point = Point()
            val display : Display = windowManager.getDefaultDisplay() // deprecated in API 30
            display.getSize(size) // deprecated in API 30
            //val height  : Int = size.y
            size.x //val width : Int = size.x
        }
    }

    protected fun onSwipeMove(currentLead : Float, currentTrail : Float, swipeState : SwipeState?) : Float {
        logDebug(TAG,"onSwipeMove($currentLead Lead $cardViewLeading $cardViewLeadEdge - $cardViewTrailEdge $cardViewTrailing Trail $currentTrail)")
        return when {
            swipeState == SwipeState.NONE -> { /**For Swipe None Only */ logDebug(TAG,"SwipeState.NONE")
                cardViewLeading
            }
            swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {
                logDebug(TAG,"Swipe Left Edge")
                currentLead
            }
            swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailing -> {
                logDebug(TAG,"Swipe Left")
                currentLead
            }
            swipeState == SwipeState.RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                logDebug(TAG,"Swipe Right Edge")
                currentLead
            }
            swipeState == SwipeState.RIGHT && currentLead > cardViewLeading && currentTrail > cardViewTrailing -> {
                logDebug(TAG,"Swipe Right")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {
                logDebug(TAG,"Swipe Left Edge")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                logDebug(TAG,"Swipe Right Edge")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailing -> {
                logDebug(TAG,"Swipe Left")
                currentLead
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeading && currentTrail > cardViewTrailing -> {
                logDebug(TAG,"Swipe Right")
                currentLead
            }
            else -> { /**For Unknown Swipe Only */ logDebug(TAG,"Unknown Swipe ${swipeState}")
                cardViewLeading
            }
        }
    }

    protected fun onSwipeUp() : Float { logDebug(TAG,"onSwipeUp()")
        return cardViewLeading
    }

    abstract fun bindDataToViewHolder(model : CustomHolderModel, position : Int, swipeState : SwipeState)
}