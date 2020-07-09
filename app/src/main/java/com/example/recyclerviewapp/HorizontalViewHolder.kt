package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

class HorizontalViewHolder : BaseHorizontalViewHolder {

    companion object {
        private val TAG = HorizontalViewHolder::class.java.simpleName
    }
    //region cell_vertical_sample
    val imageView : ImageView
    val textView : TextView
    val cardView : CardView
    //endregion
    constructor(context : Context, itemView : View)  : super(context, itemView) {

    }

    init {
        imageView = itemView.findViewById(R.id.image_view)
        textView = itemView.findViewById(R.id.text_view)
        cardView = itemView.findViewById(R.id.card_view)
    }

    override fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        imageView.setBackgroundResource(item.icon?:0)
        textView.setText(item.name)
        //endregion
        //region Set Listener
        /* On Click */
        cardView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                //customListeners.onClick(item, position)
            }
        })
        /* On Long Click */
        cardView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(view: View): Boolean {
                //customListeners.onLongClick(item, position)
                return false
            }
        })
        //endregion
    }
}
