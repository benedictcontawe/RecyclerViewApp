package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomNestedHorizontalViewHolder : BaseNestedViewHolder {

    companion object {
        private val TAG = CustomNestedHorizontalViewHolder::class.java.simpleName
    }
    //region list_horizontal_sample
    val recyclerView : RecyclerView
    lateinit var horizontalAdapter : CustomHorizontalAdapter
    //endregion
    constructor(context : Context, itemView : View)  : super(context, itemView) {

    }

    init {
        recyclerView = itemView.findViewById(R.id.recycler_view_horizontal)
    }

    override fun bindDataToViewHolder(items : MutableList<CustomViewModel>) {
        horizontalAdapter = CustomHorizontalAdapter()
        recyclerView.setAdapter(horizontalAdapter)
        horizontalAdapter.setItems(items)
    }
}