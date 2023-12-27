package com.example.recyclerviewapp

import androidx.compose.runtime.Immutable

@Immutable
public data class NameModel (
    private val name : String,
    private val detail : String,
) : CustomModel() {

    companion object {
        private val TAG = NameModel::class.java.getSimpleName()
    }

    override fun getName(): String = name

    override fun getDetail() : String = detail

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NameModel

        if (name != other.name) return false
        if (detail != other.detail) return false

        return true
    }

    override fun hashCode() : Int {
        return super.hashCode()
    }

    override fun toString() : String {
        return "${TAG}($name, $detail)" ?: super.toString()
    }
}