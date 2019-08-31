package com.example.recyclerviewapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter : RecyclerView.Adapter<CustomViewHolder>{

    companion object {
        const val DefaultView = 0
        const val IconView = 1
        const val NameView = 2
    }

    /**Main */
    private lateinit var context : Context
    private lateinit var customListeners : CustomListeners

    private lateinit var list : MutableList<CustomViewModel>
    //private lateinit var list : List<CustomViewModel>

    constructor(context : Context, customListeners : CustomListeners) : super(){
        this.context = context
        this.customListeners = customListeners
    }

    init {
        list = mutableListOf()
        //list = listOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        //val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        //val view : View = layoutInflater.inflate(R.layout.item_sample, parent, false)
        //return CustomViewHolder(context, view, customListeners)

        val layoutInflater = LayoutInflater.from(context)
        var view : View
        //TODO Create xml layout for IconViewType, NameViewType
        when(viewType){
            DefaultView -> {
                view = layoutInflater.inflate(R.layout.item_sample, parent, false)
            }
            IconView -> {
                view = layoutInflater.inflate(R.layout.item_icon_sample, parent, false)
            }
            NameView -> {
                view = layoutInflater.inflate(R.layout.item_name_sample, parent, false)
            }
            else -> {
                view = layoutInflater.inflate(R.layout.item_sample, parent, false)
            }
        }
        return CustomViewHolder(context, view, customListeners)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //TODO Create bind data method for IconViewType, NameViewType
        when(list[position].viewType) {
            CustomViewModel.DefaultViewType -> { holder.bindDataToViewHolder(list[position], position) }
            CustomViewModel.IconViewType -> { holder.bindDataToViewHolder(list[position], position) }
            CustomViewModel.NameViewType -> { holder.bindDataToViewHolder(list[position], position) }
            else -> { DefaultView }
        }
    }

    override fun getItemCount() : Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        return when(list[position].viewType) {
            CustomViewModel.DefaultViewType -> { DefaultView }
            CustomViewModel.IconViewType -> { IconView }
            CustomViewModel.NameViewType -> { NameView }
            else -> { DefaultView }
        }
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