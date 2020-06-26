package com.example.recyclerviewapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper

class CustomAdapter : RecyclerView.Adapter<CustomViewHolder>, CustomItemTouchHelperListener {

    companion object {
        private var TAG : String = CustomAdapter::class.java.simpleName
    }
    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners
    private lateinit var itemTouchHelper : ItemTouchHelper

    private lateinit var list : MutableList<CustomViewModel>
    //private lateinit var list : List<CustomViewModel>

    constructor(context : Context, customListeners : CustomListeners) : super() {
        this.context = context
        this.customListeners = customListeners
    }

    init {
        list = mutableListOf()
        //list = listOf()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CustomViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(R.layout.item_sample, parent, false);
        return CustomViewHolder(context, view, customListeners, itemTouchHelper)
    }

    override fun onBindViewHolder(holder : CustomViewHolder, position : Int) {
        holder.bindDataToViewHolder(list[position], position)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    public fun setItems(items : MutableList<CustomViewModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun insertItem(item : CustomViewModel, position : Int) {
        list.add(position, item)
        notifyItemInserted(position)
    }

    fun insertItems(items : List<CustomViewModel>, position : Int) {
        list.addAll(items)
        notifyItemRangeChanged(position, itemCount)
    }

    fun updateItem(item : CustomViewModel, position : Int) {
        list[position] = item
        notifyItemChanged(position)
    }

    fun deleteItem(position : Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItems() : List<CustomViewModel> {
        return list
    }

    fun deleteAllItems() {
        list.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    fun setTouchHelper(itemTouchHelper : ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    override fun onItemMove(fromPosition : Int, toPosition : Int) {
        val x = list.get(fromPosition)
        list.remove(x)
        list.add(toPosition, x)
        notifyItemMoved(fromPosition, toPosition)
        customListeners.onItemMoved(list[toPosition], fromPosition, toPosition)
    }

    override fun onItemSwiped(position : Int) {
        deleteItem(position)
    }

    override fun onItemClear(position : Int) {
        Log.d(TAG,"onItemDismiss($position)")
    }
}