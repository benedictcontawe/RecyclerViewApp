package com.example.recyclerviewapp

data class CustomModel (
    public val id : Int, public val icon : Int, public val name : String
) {

    private var page : Int? = null ?: 0

    constructor(id : Int, icon : Int, name : String, page : Int = 0) : this(id = id, icon = icon, name = name) {
        this.page = page
    }

    public fun setPage(page : Int) {
        this.page = page
    }

    public fun getPage() : Int {
        return this.page ?: 0
    }

    override fun equals(other : Any?) : Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + icon
        result = 31 * result + name.hashCode()
        result = 31 * result + (page ?: 0)
        return result ?: super.hashCode()
    }

    override fun toString(): String {
        return "CustomModel id - $id icon - $icon name - $name page - $page" ?: super.toString()
    }
}