package com.example.recyclerviewapp.view.holder

import android.content.Context
import android.view.View
import com.example.recyclerviewapp.CustomListeners
import com.example.recyclerviewapp.databinding.MovieBinder
import com.example.recyclerviewapp.model.CustomViewModel

class CustomViewHolder : BaseViewHolder {

    /**Data */
    private lateinit var movieBinder : MovieBinder
    /**With Events and Others */
    //private lateinit var cardView: CardView

    constructor(context: Context, customListeners: CustomListeners, movieBinder : MovieBinder) : super(context,customListeners,movieBinder.root){
        this.movieBinder = movieBinder
    }

    override fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        id = item.id
        movieBinder.title.text = item.title?:"null"
        movieBinder.release.text = item.release?:"null"
        movieBinder.actor.text = item.actor?:"null"
        movieBinder.director.text = item.director?:"null"
        movieBinder.ratingBar.rating = item.rating?:0.0f
        movieBinder.ratingText.text = item.rating.toString()
        //endregion
        //region Set Listener
        /* On Click */
        movieBinder.constraintLayout.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View) {
                getListener().onClick(item, position)
            }
        })
        /* On Long Click */
        /*
        movieBinder.constraintLayout.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(view: View): Boolean {
                getListener().onLongClick(item, position)
                return false
            }
        })
        */
        //endregion
    }
}