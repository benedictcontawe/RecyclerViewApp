package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class CustomIconViewHolder : BaseViewHolder {

    /**Data */
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    /**With Events and Others */
    private lateinit var cardView: CardView

    constructor(context : Context, itemView : View, customListeners: CustomListeners) : super(context, itemView, customListeners)

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