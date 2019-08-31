package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class CustomNameViewHolder : BaseViewHolder {

    /**Data */
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    /**With Events and Others */
    private lateinit var cardView: CardView

    constructor(context : Context, itemView : View, customListeners: CustomListeners) : super(context, itemView, customListeners)

    init {
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
    }
    //TODO: Create bind data for IconView, and NameView
    public override fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        textView.setText(item.name)
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