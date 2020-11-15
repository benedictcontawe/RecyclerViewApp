package com.example.recyclerviewapp;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private static String TAG = CustomViewHolder.class.getSimpleName();
    /**Main */
    //private Context var context;
    private CustomListeners customListeners;
    private ItemTouchHelper itemTouchHelper;
    private GestureDetector gestureDetector;
    /**Data */
    private View view;
    private ImageView imageView;
    private TextView textView;
    /**With Events and Others */
    private CardView cardView;
    /**bindDataToViewHolder Parameters*/
    private CustomViewModel item;
    //private int positionViewHolder;

    public CustomViewHolder(Context context, View itemView, CustomListeners customListeners, ItemTouchHelper itemTouchHelper) {
        super(itemView);
        this.customListeners = customListeners;
        this.itemTouchHelper = itemTouchHelper;

        imageView = itemView.findViewById(R.id.image_view);
        textView = itemView.findViewById(R.id.text_view);
        cardView = itemView.findViewById(R.id.card_view);
        gestureDetector = new GestureDetector(itemView.getContext(),this);
    }

    public void bindDataToViewHolder(CustomViewModel item, int position) {
        //region Input Data
        this.item = item;
        imageView.setBackgroundResource(item.icon);
        textView.setText(item.name);
        //endregion
        //region Set Listener
        cardView.setOnTouchListener(this);
        //endregion
    }
    /* On Click */
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        customListeners.onClick(item, getAdapterPosition());
        return false;
    }
    /* On Long Click */
    @Override
    public void onLongPress(MotionEvent event) {
        customListeners.onLongClick(item, getAdapterPosition());
        itemTouchHelper.startDrag(this);
    }
    /* On Touch */
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                itemTouchHelper.startDrag(this);
                break;
            default:
                itemTouchHelper.startDrag(this);
                break;
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent eventFirst, MotionEvent eventSecond, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent eventFirst, MotionEvent eventSecond, float distanceX, float distanceY) {
        return false;
    }
}