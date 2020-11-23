package com.example.recyclerviewapp

interface CustomListeners {

    public fun onClickLeft(item : CustomViewModel, position : Int)

    public fun onClickRight(item : CustomViewModel, position : Int)
}