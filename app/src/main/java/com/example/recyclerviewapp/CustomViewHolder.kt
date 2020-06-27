package com.example.recyclerviewapp

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper

class CustomViewHolder : RecyclerView.ViewHolder, View.OnTouchListener, GestureDetector.OnGestureListener {

    companion object {
        private var TAG : String = CustomViewHolder::class.java.simpleName
    }
    /**Main */
    //private lateinit var context : Context
    private lateinit var customListeners : CustomListeners
    private lateinit var itemTouchHelper : ItemTouchHelper
    private lateinit var gestureDetector : GestureDetector
    /**Data */
    //private lateinit var view : View
    private lateinit var imageView : ImageView
    private lateinit var textView : TextView
    /**With Events and Others */
    private lateinit var cardView: CardView
    /**bindDataToViewHolder Parameters*/
    private lateinit var item : CustomViewModel
    //private var positionViewHolder by Delegates.notNull<Int>()

    constructor(context : Context, itemView : View, customListeners : CustomListeners, itemTouchHelper : ItemTouchHelper) : super(itemView) {
        this.customListeners = customListeners
        this.itemTouchHelper = itemTouchHelper
    }

    init {
        imageView = itemView.findViewById(R.id.image_view)
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
        gestureDetector = GestureDetector(itemView.getContext(),this)
    }

    public fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        this.item = item
        imageView.setBackgroundResource(item.icon?:0)
        textView.setText(item.name)
        //endregion
        //region Set Listener
        cardView.setOnTouchListener(this)
        //endregion
    }
    /* On Click */
    override fun onSingleTapUp(event : MotionEvent) : Boolean {
        customListeners.onClick(item, getAdapterPosition())
        return false
    }
    /* On Long Click */
    override fun onLongPress(event : MotionEvent) {
        customListeners.onLongClick(item, getAdapterPosition())
        itemTouchHelper.startDrag(this@CustomViewHolder)
    }
    /* On Touch */
    override fun onTouch(view : View, event : MotionEvent) : Boolean {
        gestureDetector.onTouchEvent(event)
        /*
        when {
            event.getActionMasked() == MotionEvent.ACTION_DOWN -> {
                itemTouchHelper.startDrag(this@CustomViewHolder)
            }
        }
        */
        return true
    }

    override fun onShowPress(event : MotionEvent) {

    }

    override fun onDown(event : MotionEvent) : Boolean {
        return false
    }

    override fun onFling(eventFirst : MotionEvent, eventSecond : MotionEvent, velocityX : Float, velocityY : Float) : Boolean {
        return false
    }

    override fun onScroll(eventFirst : MotionEvent, eventSecond : MotionEvent, distanceX : Float, distanceY : Float) : Boolean {
        return false
    }
}