package com.example.recyclerviewapp;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.recyclerview.widget.RecyclerView;

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = BaseViewHolder.class.getSimpleName();

    public static void LogDebug(String TAG, String message) {
        Log.d(TAG,message);
    }

    public static void LogError(String TAG, String message) {
        Log.e(TAG,message);
    }

    /** Main */
    private final CustomListeners customListeners;
    /** On Swipe */
    private final WindowManager windowManager;
    private final Float cardViewLeading;
    private final Float cardViewLeadEdge;
    private final Float cardViewTrailEdge;
    private final Float cardViewTrailing;
    private final int width;
    protected Float dXLead = Float.valueOf(0);
    protected Float dXTrail = Float.valueOf(0);

    public BaseViewHolder(View itemView, CustomListeners customListeners) {
        super(itemView);
        this.customListeners = customListeners;
        windowManager = (WindowManager) itemView.getContext().getSystemService(Context.WINDOW_SERVICE);
        width = getWidth();
        cardViewLeading = Float.valueOf(width) * 0.10f; //leading
        cardViewLeadEdge = Float.valueOf(width) * 0.25f; //leading_rubber
        cardViewTrailEdge = Float.valueOf(width) * 0.75f; //trailing_rubber
        cardViewTrailing = Float.valueOf(width) * 0.90f; //trailing
    }

    protected CustomListeners getListener() {
        return customListeners;
    }

    private int getWidth() {
        LogDebug(TAG,"getWidth " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            LogDebug(TAG,"getWidth >= R");
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
            WindowInsets windowInsets = windowMetrics.getWindowInsets();
            Insets insets = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
            int insetsWidth = insets.right + insets.left;
            //int insetsHeight = insets.top + insets.bottom;
            Rect bounds = windowMetrics.getBounds();
            //int height  = bounds.height() - insetsHeight;
            return bounds.width() - insetsWidth; //Int width = bounds.width() - insetsWidth;
        } else {
            LogDebug(TAG,"getWidth < R");
            Point size = new Point();
            Display display = windowManager.getDefaultDisplay(); //activity.getWindowManager().getDefaultDisplay(); // deprecated in API 30
            display.getSize(size); // deprecated in API 30
            //int height = size.y;
            return size.x; //Int width = size.x;
        }
    }

    protected void setSwipe(View view, SwipeState swipeState) {
        onAnimate(view, onSwipeUp(swipeState),Long.valueOf(0));
    }

    protected void onAnimate(View view, Float dx, Long duration) {
        view.animate().x(dx).setDuration(duration).start();
    }

    protected Float onSwipeMove(Float currentLead, Float currentTrail, SwipeState swipeState) {
        LogDebug(TAG,"onSwipeMove($currentLead, $currentTrail, $swipeState)");
        if (swipeState == SwipeState.LEFT || swipeState == SwipeState.RIGHT || swipeState == SwipeState.LEFT_RIGHT) {
            return currentLead;
        } else {
            return cardViewLeading;
        }
    }

    protected SwipeState getSwipeState(Float currentLead, Float currentTrail, SwipeState swipeState) {
        LogDebug(TAG,"getSwipeState($currentLead, $currentTrail, $swipeState)");
        if (swipeState == SwipeState.LEFT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge) {
            LogDebug(TAG,"SwipeState.LEFT");
            return SwipeState.LEFT;
        } else if (swipeState == SwipeState.RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing) {
            LogDebug(TAG,"SwipeState.RIGHT");
            return SwipeState.RIGHT;
        } else if (swipeState == SwipeState.LEFT_RIGHT && currentLead < cardViewLeading && currentTrail < cardViewTrailEdge) {
            LogDebug(TAG,"SwipeState.LEFT");
            return SwipeState.LEFT;
        } else if (swipeState == SwipeState.LEFT_RIGHT && currentLead > cardViewLeadEdge && currentTrail > cardViewTrailing) {
            LogDebug(TAG,"SwipeState.RIGHT");
            return SwipeState.RIGHT;
        } else {
            LogDebug(TAG,"SwipeState.NONE");
            return SwipeState.NONE;
        }
    }

    protected Float onSwipeUp(SwipeState swipeState) {
        LogDebug(TAG,"onSwipeUp($swipeState)");
        LogDebug(TAG,"$cardViewLeading $cardViewLeadEdge $cardViewTrailEdge $cardViewTrailing - ${size.x.toFloat()}");
        if (swipeState == SwipeState.NONE) return cardViewLeading;
        else if (swipeState == SwipeState.LEFT) return (Float.valueOf(width) * -0.05f);
        else if (swipeState == SwipeState.RIGHT) return cardViewLeadEdge;
        else return cardViewLeading;
    }

    abstract void bindDataToViewHolder(CustomViewModel item, int position, SwipeState swipeState);
}