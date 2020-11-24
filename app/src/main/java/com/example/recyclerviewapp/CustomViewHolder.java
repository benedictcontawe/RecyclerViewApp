package com.example.recyclerviewapp;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class CustomViewHolder extends BaseViewHolder {

    private static String TAG = CustomViewHolder.class.getSimpleName();

    /**Data */
    private ImageView imageView;
    private TextView textView;
    /**With Events and Others */
    private ImageView leftImage;
    private ImageView rightImage;
    private CardView cardView;

    public CustomViewHolder(Context context, View itemView, CustomListeners customListeners) {
        super(context, itemView, customListeners);
        imageView = itemView.findViewById(R.id.image_view);
        textView = itemView.findViewById(R.id.text_view);
        cardView = itemView.findViewById(R.id.card_view);
        leftImage = itemView.findViewById(R.id.button_left);
        rightImage = itemView.findViewById(R.id.button_right);
    }

    @Override
    void bindDataToViewHolder(final CustomViewModel item, final int position, final SwipeState swipeState) {
        //region Input Data
        imageView.setBackgroundResource(item.icon);
        textView.setText(item.name);
        setSwipe(cardView, item.state);
        //endregion
        //region Set Event Listener
        /* On Click */
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListener().onClickLeft(item, position);
            }
        });
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListener().onClickRight(item, position);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Do not remove this need this click listener to swipe with on touch listener
                LogDebug(TAG, "on Click Card");
            }
        });
        /* On Touch Swipe */
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dXLead = view.getX() - event.getRawX();
                        dXTrail = view.getRight() - event.getRawX();
                        LogDebug(TAG, "MotionEvent.ACTION_DOWN");
                        return false;
                    case MotionEvent.ACTION_MOVE:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        onAnimate(view, onSwipeMove(event.getRawX() + dXLead, event.getRawX() + dXTrail,swipeState),Long.valueOf(0));
                        item.state = getSwipeState(event.getRawX() + dXLead, event.getRawX() + dXTrail, swipeState);
                        LogDebug(TAG, "MotionEvent.ACTION_MOVE");
                        return false;
                    case MotionEvent.ACTION_UP:
                        onAnimate(view, onSwipeUp(item.state),Long.valueOf(250));
                        LogDebug(TAG, "MotionEvent.ACTION_UP");
                        return false;
                    default:
                        return true;
                }
            }
        });
    }
}
