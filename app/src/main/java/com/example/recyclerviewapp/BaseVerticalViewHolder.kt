package com.example.recyclerviewapp

import android.content.Context
import android.util.Log
import android.view.View

abstract class BaseVerticalViewHolder : BaseViewHolder {

    companion object {
        private val TAG = BaseVerticalViewHolder::class.java.simpleName
    }

    constructor(context : Context, itemView : View) : super(context, itemView) {
        Log.d("BaseViewHolder", "constructor")
    }

    abstract fun bindDataToViewHolder(item : CustomViewModel, position : Int)
}