package com.example.recyclerviewapp

public interface CustomListeners {

    fun onClick(item : CustomViewModel, position : Int)

    fun onLongClick(item : CustomViewModel, position : Int)
}