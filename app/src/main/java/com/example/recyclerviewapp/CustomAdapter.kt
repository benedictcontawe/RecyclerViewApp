package com.example.recyclerviewapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter : RecyclerView.Adapter<BaseViewHolder>{

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View
        when(viewType){
            DefaultView -> {
                view = layoutInflater.inflate(R.layout.item_sample, parent, false)
                return CustomViewHolder(context, view, customListeners)
            }
            IconView -> {
                view = layoutInflater.inflate(R.layout.item_icon_sample, parent, false)
                return CustomIconViewHolder(context, view, customListeners)
            }
            NameView -> {
                view = layoutInflater.inflate(R.layout.item_name_sample, parent, false)
                return CustomNameViewHolder(context, view, customListeners)
            }
            else -> {
                view = layoutInflater.inflate(R.layout.item_sample, parent, false)
                return CustomViewHolder(context, view, customListeners)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
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

    public fun changeView(viewType : Int, position: Int) {
        list[position].viewType = viewType
        notifyItemChanged(position)
    }

    public fun resetViews() {
        /*
        list.filter { it.viewType != CustomViewModel.DefaultViewType }
            .map {
                it.viewType = CustomViewModel.DefaultViewType
                notifyDataSetChanged()
            }
        for (i in 0 .. list.size - 1 ) {
            if (list[i].viewType != CustomViewModel.DefaultViewType) {
                list[i].viewType = CustomViewModel.DefaultViewType
                notifyItemChanged(i)
            }
        }
        */
        for (i in 0 until list.size) {
            if (list[i].viewType != CustomViewModel.DefaultViewType) {
                list[i].viewType = CustomViewModel.DefaultViewType
                notifyItemChanged(i)
            }
        }
    }

    public fun setItems(items : MutableList<CustomViewModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    public fun insertItem(item : CustomViewModel, position : Int) {
        list.add(position, item)
        notifyItemInserted(position)
    }

    public fun insertItems(items : List<CustomViewModel>, position : Int) {
        list.addAll(items)
        notifyItemRangeChanged(position, itemCount)
    }

    public fun updateItem(item : CustomViewModel, position: Int) {
        list[position] = item
        notifyItemChanged(position)
    }

    public fun deleteItem(position : Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    public fun deleteAllItems() {
        list.clear()
        notifyItemRangeRemoved(0, itemCount)
    }
}