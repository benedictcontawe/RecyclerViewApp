package com.example.recyclerviewapp.model

class CustomModel {

    var id: Int? = null
    var title: String? = null
    var director: String? = null
    var actors: List<String>
    var genre: String//var genre: MovieGenre
    var release: String? = null
    var plot: String? = null

    var rating: Float? = null

    constructor(id: Int, title: String, director: String, actors: List<String>, genre: String, release: String,  plot: String) {
        this.id = id
        this.title = title
        this.director = director
        this.actors = actors
        this.genre = genre
        this.release = release
        this.plot = plot
        this.rating = 0.0f
    }

    constructor(id: Int, title: String, director: String, actors: List<String>, genre: String, release: String,  plot: String, rating: Float) {
        this.id = id
        this.title = title
        this.director = director
        this.actors = actors
        this.genre = genre
        this.release = release
        this.plot = plot
        this.rating = rating
    }
}
