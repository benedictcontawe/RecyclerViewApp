package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomNestedVerticalViewHolder : BaseNestedViewHolder {

    companion object {
        private val TAG = CustomNestedVerticalViewHolder::class.java.simpleName
    }
    //region list_vertical_sample
    var recyclerView : RecyclerView
    lateinit var verticalAdapter : CustomVerticalAdapter
    //endregion
    constructor(context : Context, itemView : View)  : super(context, itemView) {

    }

    init {
        recyclerView = itemView.findViewById(R.id.recycler_view_vertical)
    }

    override fun bindDataToViewHolder(items : MutableList<CustomViewModel>) {
        verticalAdapter = CustomVerticalAdapter()
        recyclerView.setAdapter(verticalAdapter)
        verticalAdapter.setItems(items)
    }
}