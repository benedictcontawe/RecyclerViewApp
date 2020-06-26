package com.example.recyclerviewapp

interface CustomItemTouchHelperListener {

    fun onItemMove(fromPosition : Int, toPosition : Int)

    fun onItemSwiped(position : Int)

    fun onItemClear(position : Int)
}