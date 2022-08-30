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

    public fun setSwipe(view : View, swipeState : SwipeState?) { logDebug(TAG, "setSwipe($view ,$swipeState)")
        onAnimate(view, onSwipeUp(swipeState),0)
    }

    public fun onAnimate(view : View, dx : Float, duration : Long) {
        view.animate().x(dx).setDuration(duration).start()
    }

    public fun onSwipeMove(currentLead : Float, currentTrail : Float, swipeState : SwipeState?) : Float {
        logDebug(TAG,"onSwipeMove($currentLead, $currentTrail, $swipeState)")
        return when {
            swipeState == SwipeState.LEFT || swipeState == SwipeState.RIGHT || swipeState == SwipeState.LEFT_RIGHT -> {
                currentLead
            }
            else -> { /**swipeState == SwipeState.NONE*/
                logDebug(TAG,"Else Swipe ${swipeState}")
                cardViewLeading
            }
        }
    }

    public fun getSwipeState(currentLead : Float, currentTrail : Float, swipeState : SwipeState?) : SwipeState {
        logDebug(TAG,"getSwipeState($currentLead, $currentTrail, $swipeState)")
        return when {
            swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {
                logDebug(TAG,"SwipeState.LEFT")
                SwipeState.LEFT
            }
            swipeState == SwipeState.RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                logDebug(TAG,"SwipeState.RIGHT")
                SwipeState.RIGHT
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge -> {
                logDebug(TAG,"SwipeState.LEFT")
                SwipeState.LEFT
            }
            swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing -> {
                logDebug(TAG,"SwipeState.RIGHT")
                SwipeState.RIGHT
            }
            else -> {
                logDebug(TAG,"SwipeState.NONE")
                SwipeState.NONE
            }
        }
    }

    public fun onSwipeUp(swipeState : SwipeState?) : Float {
        logDebug(TAG,"onSwipeUp($swipeState)")
        logDebug(TAG,"$cardViewLeading $cardViewLeadEdge $cardViewTrailEdge $cardViewTrailing - ${width.toFloat()}")
        return if (swipeState == SwipeState.NONE) cardViewLeading
        else if (swipeState == SwipeState.LEFT) (width.toFloat() * -0.05f)
        else if (swipeState == SwipeState.RIGHT) cardViewLeadEdge
        else cardViewLeading
    }

    abstract fun bindDataToViewHolder(model : CustomHolderModel, position : Int, swipeState : SwipeState?)
}