package com.example.recyclerviewapp.view.holder

import android.content.Context
import android.view.View
import com.example.recyclerviewapp.CustomListeners
import com.example.recyclerviewapp.databinding.MovieBinder
import com.example.recyclerviewapp.model.CustomModel

class CustomViewHolder : BaseViewHolder {

    /**Data */
    private lateinit var movieBinder : MovieBinder
    /**With Events and Others */
    //private lateinit var cardView: CardView

    constructor(context : Context, customListeners : CustomListeners, movieBinder : MovieBinder) : super(context, customListeners, movieBinder.root){
        this.movieBinder = movieBinder
    }

    override fun bindDataToViewHolder(item : CustomModel, position : Int) {
        //region Input Data
        id = item.id
        //movieBinder.title.text = item.title?:"null" //Data Binder Handled this
        //movieBinder.release.text = item.release?:"null" //Data Binder Handled this
        //movieBinder.actor.text = item.actors.get(0) //Data Binder Handled this
        //movieBinder.director.text = item.director?:"null" //Data Binder Handled this
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