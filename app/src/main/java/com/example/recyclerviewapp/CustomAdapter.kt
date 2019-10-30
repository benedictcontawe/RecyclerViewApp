package com.example.recyclerviewapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter : RecyclerView.Adapter<CustomViewHolder> {

    /**Main */
    private lateinit var context : Context
    private lateinit var list: MutableList<CustomViewModel>

    constructor(context : Context) : super() {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_focused, parent, false)

        return CustomViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindDataToViewHolder(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(items: MutableList<CustomViewModel>) {
        list = items
        //list.clear();
        //list.addAll(items);
        notifyDataSetChanged()
    }

    fun insertItem(item: CustomViewModel, position: Int) {
        list.add(position, item)
        notifyItemInserted(position)
    }

    fun insertItems(items: List<CustomViewModel>, position: Int) {
        list.addAll(items)
        notifyItemRangeChanged(position, itemCount)
    }

    fun updateItem(item: CustomViewModel, position: Int) {
        list[position] = item
        notifyItemChanged(position)
    }

    fun deleteItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun deleteAllItems() {
        list.clear()
        notifyItemRangeRemoved(0, itemCount)
    }
}
