package com.example.recyclerviewapp

import java.io.Serializable

sealed class CustomModel () : Serializable {

    public abstract fun getName() : String

    public abstract fun getDetail() : String
}