package com.example.recyclerviewapp

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder : RecyclerView.ViewHolder {

    companion object {
        private val TAG = BaseViewHolder::class.java.simpleName
    }

    private lateinit var context : Context

    constructor(context : Context, itemView : View) : super(itemView) {
        Log.d(TAG, "constructor")
        this.context = context
    }

    public fun getContext() : Context = context
}