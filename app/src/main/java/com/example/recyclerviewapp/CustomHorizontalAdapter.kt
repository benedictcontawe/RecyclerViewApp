package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomHorizontalAdapter : RecyclerView.Adapter<BaseHorizontalViewHolder>  {

    companion object {
        private var TAG: String = CustomHorizontalAdapter::class.java.simpleName
    }

    private lateinit var list : MutableList<CustomViewModel>

    constructor() : super() {

    }

    init {
        list = mutableListOf()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseHorizontalViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View = layoutInflater.inflate(R.layout.cell_horizontal_sample, parent, false);
        return HorizontalViewHolder(parent.getContext(), view)
    }

    override fun onBindViewHolder(holder : BaseHorizontalViewHolder, position : Int) {
        holder.bindDataToViewHolder(list.get(position), position)
    }

    override fun getItemCount() : Int {
        return list.size
    }

    public fun setItems(items : MutableList<CustomViewModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}