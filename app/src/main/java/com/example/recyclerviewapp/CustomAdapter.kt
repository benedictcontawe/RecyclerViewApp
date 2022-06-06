package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

public class CustomAdapter : PagingDataAdapter<CustomModel, CustomViewHolder> {
    /** Main */
    private lateinit var customListeners : CustomListeners

    constructor(customListeners : CustomListeners) : super(CustomDiffCallback) {
        this.customListeners = customListeners
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : CustomViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.getContext())
        val view : View = layoutInflater.inflate(R.layout.item_sample, parent, false);
        return CustomViewHolder(parent.getContext(), view, customListeners)
    }

    override fun onBindViewHolder(holder : CustomViewHolder, position : Int) {
        holder.bindDataToViewHolder( item = getItem(position), position = position)
    }
}
/*
loadStateFlow.collectLatest {
    it.refresh
    it.prepend
    it.append
}
*/
