package com.example.recyclerviewapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.text.method.TextKeyListener.clear



class CustomAdapter : RecyclerView.Adapter<CustomViewHolder>{

    /**Main */
    private lateinit var context : Context
    private lateinit var activity : Activity
    private lateinit var customListeners : CustomListeners

    private lateinit var list : MutableList<CustomViewModel>
    //private lateinit var list : List<CustomViewModel>

    private val swipeState : Int

    constructor(context : Context, customListeners : CustomListeners, swipeState : Int) : super() {
        this.context = context
        this.customListeners = customListeners
        this.swipeState = swipeState
    }

    init {
        list = mutableListOf()
        //list = listOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(R.layout.item_sample, parent, false)
        return CustomViewHolder(context, view, customListeners)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindDataToViewHolder(list[position], position, activity, swipeState)
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

    fun updateItem(item : CustomViewModel, position: Int) {
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