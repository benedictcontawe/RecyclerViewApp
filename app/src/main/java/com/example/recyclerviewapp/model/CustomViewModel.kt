package com.example.recyclerviewapp.model

class CustomViewModel {

    var id: String? = null
    var title: String? = null
    var release: String? = null
    var actor: String? = null
    var director: String? = null
    var rating: Float? = null

    var genre: MovieGenre
    var plot: String? = null

    constructor(id: String, title: String, release: String, actor: String, director: String, rating: Float, plot: String, genre: MovieGenre?) {
        this.id = id
        this.title = title
        this.release = release
        this.actor = actor
        this.director = director
        this.rating = rating
        this.genre = genre?: MovieGenre.ACTION
        this.plot = plot
    }
}
