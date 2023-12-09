package com.example.recyclerviewapp

import java.io.Serializable

data class CustomModel (
    //public val id : Int,
    public val name : String,
    public val detail : String,
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomModel

        if (name != other.name) return false
        if (detail != other.detail) return false

        return true
    }

    override fun hashCode() : Int {
        return super.hashCode()
    }

    override fun toString() : String {
        return super.toString()
    }
}