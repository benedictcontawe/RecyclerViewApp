package com.example.recyclerviewapp

import android.content.Context
import android.util.Log
import android.view.View

abstract class BaseNestedViewHolder : BaseViewHolder {

    companion object {
        private val TAG = BaseNestedViewHolder::class.java.simpleName
    }

    constructor(context : Context, itemView : View) : super(context, itemView) {
        Log.d("BaseViewHolder", "constructor")
    }

    abstract fun bindDataToViewHolder(items : MutableList<CustomViewModel>)
}