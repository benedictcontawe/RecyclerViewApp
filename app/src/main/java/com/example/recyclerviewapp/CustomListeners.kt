package com.example.recyclerviewapp

interface CustomListeners {

    fun onClick(item : CustomModel?, position: Int)

    fun onLongClick(item : CustomModel?, position: Int)
}