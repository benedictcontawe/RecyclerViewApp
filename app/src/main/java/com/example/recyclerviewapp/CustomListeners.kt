package com.example.recyclerviewapp

import com.example.recyclerviewapp.model.CustomViewModel

interface CustomListeners {

    fun onClick(item : CustomViewModel, position: Int)

    //fun onLongClick(item : MovieViewModel, position: Int)
}