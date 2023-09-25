package com.example.recyclerviewapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    /**Main */
    private val customListeners : CustomListeners
    /**Data */
    public var id : Int? = null

    constructor(itemView : View, customListeners : CustomListeners) : super(itemView) {
        this.customListeners = customListeners
    }

    protected fun getListener() : CustomListeners {
        return customListeners
    }

    abstract fun bindDataToViewHolder(item : CustomViewModel, position : Int)
}