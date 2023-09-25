package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class CustomNameViewHolder : BaseViewHolder {

    /**Data */
    private val textView : TextView
    /**With Events and Others */
    private val cardView: CardView

    constructor(itemView : View, customListeners : CustomListeners) : super(itemView, customListeners)

    init {
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
    }

    public override fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        id = item.id
        textView.setText(item.name)
        //endregion
        //region Set Listener
        /* On Click */
        cardView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view : View) {
                getListener().onClick(item, position)
            }
        })
        //endregion
    }
}