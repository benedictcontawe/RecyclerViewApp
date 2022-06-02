package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import android.widget.TextView

class CustomViewHolder : RecyclerView.ViewHolder {

    /**Main */
    private lateinit var context: Context
    private lateinit var customListeners: CustomListeners
    /**Data */
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    /**With Events and Others */
    private lateinit var cardView: CardView

    constructor(context : Context, itemView : View, customListeners: CustomListeners) : super(itemView){
        this.context = context
        this.customListeners = customListeners
    }

    init {
        imageView = itemView.findViewById(R.id.image_view)
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
    }

    public fun bindDataToViewHolder(item : CustomModel, position : Int) {
        //region Input Data
        imageView.setBackgroundResource(item.icon?:0)
        textView.setText(item.name)
        //endregion
        //region Set Listener
        /* On Click */
        cardView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                customListeners.onClick(item, position)
            }
        })
        /* On Long Click */
        cardView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(view: View): Boolean {
                customListeners.onLongClick(item, position)
                return false
            }
        })
        //endregion
    }
}