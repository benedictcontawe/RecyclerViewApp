package com.example.recyclerviewapp

class CustomViewModel {

    var id : Int? = null
    var icon : Int? = null
    var name : String? = null
    var viewType : Int? = null


    constructor(id : Int, icon : Int, name : String) {
        this.id = id
        this.icon = icon
        this.name = name
        this.viewType = DefaultViewType
    }

    constructor(id : Int, icon : Int, name : String, viewType : Int) {
        this.id = id
        this.icon = icon
        this.name = name
        this.viewType = viewType
    }

    companion object {
        const val DefaultViewType = 0
        const val IconViewType = 1
        const val NameViewType = 2
    }
}
