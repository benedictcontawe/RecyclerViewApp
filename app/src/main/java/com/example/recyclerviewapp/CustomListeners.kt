package com.example.recyclerviewapp

import com.example.recyclerviewapp.model.CustomModel

interface CustomListeners {

    fun onClick(item : CustomModel, position: Int)

    //fun onLongClick(item : MovieViewModel, position: Int)
}