package com.example.recyclerviewapp

class CustomViewModel {

    var icon: Int? = null
    var name: String? = null

    constructor(icon: Int, name: String) {
        this.icon = icon
        this.name = name
    }

    companion object {
        const val SWIPE_NONE = 0
        const val SWIPE_LEFT = 1
        const val SWIPE_RIGHT = 2
        const val SWIPE_LEFT_RIGHT = 3
    }
}
