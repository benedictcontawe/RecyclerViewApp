package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners
    /**Data */
    public var id : Int? = null

    constructor(context : Context, itemView : View, customListeners: CustomListeners) : super(itemView){
        this.context = context
        this.customListeners = customListeners
    }

    public fun getContext() : Context {
        return context
    }

    public fun getListener() : CustomListeners {
        return customListeners
    }

    abstract fun bindDataToViewHolder(item : CustomViewModel, position : Int)
}