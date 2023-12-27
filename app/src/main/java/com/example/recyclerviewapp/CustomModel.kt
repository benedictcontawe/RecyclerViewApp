package com.example.recyclerviewapp

import java.io.Serializable

public sealed class CustomModel () : Serializable {

    public abstract fun getName() : String

    public abstract fun getDetail() : String
}