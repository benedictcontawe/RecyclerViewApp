package com.example.recyclerviewapp

interface ContactListener {

    fun onClickItemEdit(item : ContactViewHolderModel, position : Int)
    fun onClickItemDelete(item : ContactViewHolderModel, position : Int)
}