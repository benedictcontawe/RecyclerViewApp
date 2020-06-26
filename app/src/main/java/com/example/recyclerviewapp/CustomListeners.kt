package com.example.recyclerviewapp

interface CustomListeners {

    fun onClick(item : CustomViewModel, position: Int)

    fun onLongClick(item : CustomViewModel, position: Int)

    fun onItemMoved(item : CustomViewModel, fromPosition : Int, toPosition : Int)
}