package com.example.recyclerviewapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewapp.model.CustomViewModel
import com.example.recyclerviewapp.model.MovieGenre

class MainViewModel : ViewModel {

    private lateinit var adapter : CustomAdapter
    private lateinit var itemList : MutableList<CustomViewModel>
    private lateinit var liveAdapter : MutableLiveData<CustomAdapter>

    constructor(context: Context, customListeners : CustomListeners) {
        adapter = CustomAdapter(context, customListeners)
    }

    init {
        liveAdapter = MutableLiveData()
    }

    public fun setItems() {
        itemList = mutableListOf<CustomViewModel>()
        itemList.clear()
        itemList.add(CustomViewModel("0", "Title 0", "Release 0", "actor 0", "Director 0", 0.5f, "Plot", MovieGenre.ACTION))
        itemList.add(CustomViewModel("1", "Title 1", "Release 1", "actor 1", "Director 1", 2.5f, "Plot", MovieGenre.COMEDY))
        itemList.add(CustomViewModel("2", "Title 2", "Release 2", "actor 2", "Director 2", 2.5f, "Plot", MovieGenre.DRAMA))
        itemList.add(CustomViewModel("3", "Title 3", "Release 3", "actor 3", "Director 3", 2.5f, "Plot", MovieGenre.HORROR))
        itemList.add(CustomViewModel("4", "Title 4", "Release 4", "actor 4", "Director 4", 2.5f, "Plot", MovieGenre.ROMANCE))
        itemList.add(CustomViewModel("5", "Title 5", "Release 5", "actor 5", "Director 5", 2.5f, "Plot", MovieGenre.ACTION))
        itemList.add(CustomViewModel("6", "Title 6", "Release 6", "actor 6", "Director 6", 2.5f, "Plot", MovieGenre.ACTION))
        itemList.add(CustomViewModel("7", "Title 7", "Release 7", "actor 7", "Director 7", 5.0f, "Plot", MovieGenre.ACTION))
        adapter.setItems(itemList)

        liveAdapter.setValue(adapter)
    }

    public fun getAdapter() : LiveData<CustomAdapter>{
        return liveAdapter
    }
}
