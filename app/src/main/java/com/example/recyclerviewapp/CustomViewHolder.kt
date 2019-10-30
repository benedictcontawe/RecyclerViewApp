package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomViewHolder : RecyclerView.ViewHolder {

    /**Main */
    private lateinit var context : Context
    /**Data */
    private val textView : TextView

    constructor(context : Context, itemView : View) : super(itemView) {
        this.context = context
    }

    init {
        this.textView = itemView.findViewById(R.id.text_view)
    }

    fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        textView.text = item.names
        //endregion
    }
}