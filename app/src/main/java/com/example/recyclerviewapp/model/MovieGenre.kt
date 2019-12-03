package com.example.recyclerviewapp.model

enum class MovieGenre {

    ACTION,
    ROMANCE,
    DRAMA,
    COMEDY,
    HORROR;

    companion object {
        fun default(): MovieGenre = ACTION
    }
}