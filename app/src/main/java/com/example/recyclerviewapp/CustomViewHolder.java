package com.example.recyclerviewapp;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class CustomViewHolder extends BaseViewHolder {

    private static final String TAG = CustomViewHolder.class.getSimpleName();

    /**Data */
    private final ImageView imageView;
    private final TextView textView;
    /**With Events and Others */
    private final ImageView leftImage, rightImage;
    private final CardView cardView;

    public CustomViewHolder(View itemView, CustomListeners customListeners) {
        super(itemView, customListeners);
        imageView = itemView.findViewById(R.id.image_view);
        textView = itemView.findViewById(R.id.text_view);
        cardView = itemView.findViewById(R.id.card_view);
        leftImage = itemView.findViewById(R.id.button_left);
        rightImage = itemView.findViewById(R.id.button_right);
    }

    @Override
    public void bindDataToViewHolder(final CustomViewModel model, final int position, final SwipeState swipeState) {
        //region Input Data
        imageView.setBackgroundResource(model.icon);
        textView.setText(model.name);
        setSwipe(cardView, model.state);
        //endregion
        setSwipeEventListener(model, position, swipeState);
    }

    private void setSwipeEventListener(final CustomViewModel model, final int position, final SwipeState swipeState) {
        //region On Click
        if (swipeState != SwipeState.NONE) {
            leftImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getListener().onClickLeft(model, position);
                }
            });
            rightImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getListener().onClickRight(model, position);
                }
            });
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //Do not remove this need this click listener to swipe with on touch listener
                LogDebug(TAG, "on Click Card");
            }
        });
        //endregion
        //region On Touch Swipe
        if (swipeState != SwipeState.NONE) {
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
                            getListener().onRetainSwipe(model, position);
                            onAnimate(view, onSwipeMove(event.getRawX() + dXLead, event.getRawX() + dXTrail,swipeState),Long.valueOf(0));
                            model.state = getSwipeState(event.getRawX() + dXLead, event.getRawX() + dXTrail, swipeState);
                            LogDebug(TAG, "MotionEvent.ACTION_MOVE");
                            return false;
                        case MotionEvent.ACTION_UP:
                            onAnimate(view, onSwipeUp(model.state),Long.valueOf(250));
                            LogDebug(TAG, "MotionEvent.ACTION_UP");
                            return false;
                        default:
                            return true;
                    }
                }
            });
        }
        //endregion
    }
}