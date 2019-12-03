package com.example.recyclerviewapp

import android.content.Context
import android.view.View
import com.example.recyclerviewapp.databinding.MovieBinder
import com.example.recyclerviewapp.model.CustomViewModel

class CustomViewHolder : BaseViewHolder {

    /**Data */
    private lateinit var movieBinder : MovieBinder
    /**With Events and Others */
    //private lateinit var cardView: CardView

    constructor(context: Context, customListeners: CustomListeners,movieBinder : MovieBinder) : super(context,customListeners,movieBinder.getRoot()){
        this.movieBinder = movieBinder
    }

    public override fun bindDataToViewHolder(item : CustomViewModel, position : Int) {
        //region Input Data
        id = item.id
        movieBinder.title.setText(item.title?:"null")
        movieBinder.release.setText(item.release?:"null")
        movieBinder.actor.setText(item.actor?:"null")
        movieBinder.director.setText(item.director?:"null")
        movieBinder.ratingBar.setRating(item.rating?:0.0f)
        movieBinder.ratingText.setText(item.rating.toString()?:"null")
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