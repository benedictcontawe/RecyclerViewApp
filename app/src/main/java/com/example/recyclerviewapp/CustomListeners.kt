package com.example.recyclerviewapp

interface CustomListeners {

    public fun onClickLeft(item : CustomHolderModel?, position : Int)

    public fun onClickRight(item : CustomHolderModel?, position : Int)
}