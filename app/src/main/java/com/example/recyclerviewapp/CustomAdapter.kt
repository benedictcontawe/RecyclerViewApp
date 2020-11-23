package com.example.recyclerviewapp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter : RecyclerView.Adapter<BaseViewHolder>{

    companion object {
        private val TAG : String = CustomAdapter::class.java.getSimpleName()
    }
    /**Main */
    private lateinit var activity : Activity
    private lateinit var customListeners : CustomListeners
    private val swipeState : SwipeState

    private var list : MutableList<CustomViewModel>

    constructor(customListeners : CustomListeners, swipeState : SwipeState) : super() {
        this.customListeners = customListeners
        this.swipeState = swipeState
    }

    init {
        list = mutableListOf<CustomViewModel>()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View = layoutInflater.inflate(R.layout.item_cell, parent, false)
        return CustomViewHolder(parent.getContext(), view, activity, customListeners)
    }

    override fun onBindViewHolder(holder : BaseViewHolder, position : Int) {
        holder.bindDataToViewHolder(list[position], position, swipeState)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    public fun setActivity(activity : Activity) {
        this.activity = activity
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

    fun deleteAllItems() {
        list.clear()
        notifyItemRangeRemoved(0, itemCount)
    }
}