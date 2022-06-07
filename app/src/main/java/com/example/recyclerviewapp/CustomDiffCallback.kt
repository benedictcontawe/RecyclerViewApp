package com.example.recyclerviewapp

import androidx.recyclerview.widget.DiffUtil

object CustomDiffCallback : DiffUtil.ItemCallback<CustomModel>() {

    override fun areItemsTheSame(oldItem : CustomModel, newItem : CustomModel) : Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem : CustomModel, newItem : CustomModel) : Boolean {
        return oldItem.name.contentEquals(newItem.name) ?: oldItem.equals(newItem)
    }
}