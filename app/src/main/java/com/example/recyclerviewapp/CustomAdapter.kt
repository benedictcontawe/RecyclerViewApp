package com.example.recyclerviewapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.databinding.MovieBinder
import com.example.recyclerviewapp.model.CustomViewModel
import com.example.recyclerviewapp.view.holder.CustomViewHolder


class CustomAdapter : RecyclerView.Adapter<CustomViewHolder>{

    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners

    private lateinit var movieBinder : MovieBinder

    private lateinit var list : MutableList<CustomViewModel>

    constructor(context : Context, customListeners : CustomListeners) : super(){
        this.context = context
        this.customListeners = customListeners
    }

    init {
        list = mutableListOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        movieBinder = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie,
                parent,
                false
        )
        return CustomViewHolder(context, customListeners, movieBinder)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        movieBinder.customModel = list.get(position)
        holder.bindDataToViewHolder(list[position], position)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    fun setItems(items : MutableList<CustomViewModel>) {
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