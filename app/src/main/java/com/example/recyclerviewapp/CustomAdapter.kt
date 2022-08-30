package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.databinding.CellItemBinder

class CustomAdapter : RecyclerView.Adapter<BaseViewHolder>{

    companion object {
        private val TAG : String = CustomAdapter::class.java.getSimpleName()
    }
    /**Main */
    private val customListeners : CustomListeners
    private val swipeState : SwipeState

    private var list : MutableList<CustomHolderModel>

    constructor(customListeners : CustomListeners, swipeState : SwipeState) : super() {
        this.customListeners = customListeners
        this.swipeState = swipeState
    }

    init {
        list = mutableListOf<CustomHolderModel>()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val binder : CellItemBinder = DataBindingUtil.inflate(layoutInflater, R.layout.cell_item, parent, false)
        return CustomViewHolder(binder, customListeners)
    }

    override fun onBindViewHolder(holder : BaseViewHolder, position : Int) {
        holder.bindDataToViewHolder(list[position], position, swipeState)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    public fun setItems(items : MutableList<CustomHolderModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun insertItem(item : CustomHolderModel, position : Int) {
        list.add(position, item)
        notifyItemInserted(position)
    }

    fun insertItems(items : List<CustomHolderModel>, position : Int) {
        list.addAll(items)
        notifyItemRangeChanged(position, itemCount)
    }

    fun updateItem(item : CustomHolderModel, position : Int) {
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