package com.example.recyclerviewapp

data class CustomModel (
    var id : Int? = null
) {
    var icon: Int? = null
    var name: String? = null

    constructor(id : Int, icon: Int, name: String) : this() {
        this.id = id
        this.icon = icon
        this.name = name
    }

    override fun equals(other : Any?) : Boolean {
        return super.equals(other)
    }
}
