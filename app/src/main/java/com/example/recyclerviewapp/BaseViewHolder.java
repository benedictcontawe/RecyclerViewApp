package com.example.recyclerviewapp;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = BaseViewHolder.class.getSimpleName();

    public void LogDebug(String TAG, String message) {
        Log.d(TAG,message);
    }

    public void LogError(String TAG, String message) {
        Log.e(TAG,message);
    }

    /** Main */
    private Context context;
    private CustomListeners customListeners;
    /** On Swipe */
    private Point size;
    private Display display;
    private WindowManager windowManager;
    private Float cardViewLeading;
    private Float cardViewLeadEdge;
    private Float cardViewTrailEdge;
    private Float cardViewTrailing;
    public Float dXLead = Float.valueOf(0);
    public Float dXTrail = Float.valueOf(0);

    public BaseViewHolder(Context context, View itemView, CustomListeners customListeners) {
        super(itemView);
        this.context = context;
        this.customListeners = customListeners;
        windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        size = new Point();
        display = windowManager.getDefaultDisplay(); //activity.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        cardViewLeading = Float.valueOf(size.x) * 0.10f; //leading
        cardViewLeadEdge = Float.valueOf(size.x) * 0.25f; //leading_rubber
        cardViewTrailEdge = Float.valueOf(size.x) * 0.75f; //trailing_rubber
        cardViewTrailing = Float.valueOf(size.x) * 0.90f; //trailing
    }

    public CustomListeners getListener() {
        return customListeners;
    }

    public void setSwipe(View view, SwipeState swipeState) {
        onAnimate(view, onSwipeUp(swipeState),Long.valueOf(0));
    }

    public void onAnimate(View view, Float dx, Long duration) {
        view.animate().x(dx).setDuration(duration).start();
    }

    public Float onSwipeMove(Float currentLead, Float currentTrail, SwipeState swipeState) {
        LogDebug(TAG,"onSwipeMove($currentLead, $currentTrail, $swipeState)");
        if (swipeState == SwipeState.LEFT || swipeState == SwipeState.RIGHT || swipeState == SwipeState.LEFT_RIGHT) {
            return currentLead;
        } else {
            return cardViewLeading;
        }
    }

    public SwipeState getSwipeState(Float currentLead, Float currentTrail, SwipeState swipeState) {
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

    public Float onSwipeUp(SwipeState swipeState) {
        LogDebug(TAG,"onSwipeUp($swipeState)");
        LogDebug(TAG,"$cardViewLeading $cardViewLeadEdge $cardViewTrailEdge $cardViewTrailing - ${size.x.toFloat()}");
        if (swipeState == SwipeState.NONE) return cardViewLeading;
        else if (swipeState == SwipeState.LEFT) return (Float.valueOf(size.x) * -0.05f);
        else if (swipeState == SwipeState.RIGHT) return cardViewLeadEdge;
        else return cardViewLeading;
    }

    abstract void bindDataToViewHolder(CustomViewModel item, int position, SwipeState swipeState);
}