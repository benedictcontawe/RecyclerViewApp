package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class CustomIconViewHolder : BaseViewHolder {

    /**Data */
    private val imageView : ImageView
    /**With Events and Others */
    private val cardView : CardView

    constructor(itemView : View, customListeners : CustomListeners) : super(itemView, customListeners)

    init {
        imageView = itemView.findViewById(R.id.image_view)
        cardView = itemView.findViewById(R.id.card_view)
    }

    public override fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        id = item.id
        imageView.setBackgroundResource(item.icon?:0)
        //endregion
        //region Set Listener
        /* On Click */
        cardView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                getListener().onClick(item, position)
            }
        })
        //endregion
    }
}