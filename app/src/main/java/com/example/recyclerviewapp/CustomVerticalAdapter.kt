package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CustomVerticalAdapter : RecyclerView.Adapter<BaseVerticalViewHolder> {

    companion object {
        private var TAG : String = CustomVerticalAdapter::class.java.simpleName
    }

    private lateinit var list : MutableList<CustomViewModel>

    constructor() : super() {

    }

    init {
        list = mutableListOf()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : BaseVerticalViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View = layoutInflater.inflate(R.layout.cell_vertical_sample, parent, false);
        return VerticalViewHolder(parent.getContext(), view)
    }

    override fun onBindViewHolder(holder : BaseVerticalViewHolder, position : Int) {
        holder.bindDataToViewHolder(list.get(position), position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    public fun setItems(items : MutableList<CustomViewModel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}