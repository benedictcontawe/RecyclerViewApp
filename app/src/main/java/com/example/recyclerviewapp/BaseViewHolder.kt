package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.model.CustomViewModel

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners
    /**Data */
    public var id : String? = null

    constructor(context: Context, customListeners: CustomListeners,view : View) : super(view) {
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