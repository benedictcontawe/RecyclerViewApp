package com.example.recyclerviewapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerviewapp.model.CustomModel
import com.example.recyclerviewapp.view.adapter.CustomAdapter

class MainViewModel : ViewModel {

    private lateinit var context: Context
    private lateinit var adapter : CustomAdapter
    private lateinit var liveAdapter : MutableLiveData<CustomAdapter>

    constructor(context: Context, customListeners : CustomListeners) {
        adapter = CustomAdapter(context, customListeners)
        this.context = context
    }

    init {
        liveAdapter = MutableLiveData()
    }

    fun setItems() {
        lateinit var itemList : MutableList<CustomModel>
        itemList = mutableListOf<CustomModel>()
        itemList.clear()
        itemList.add(CustomModel(1, "Parasite", "Joon-ho Bong", listOf("Kang-Ho Song","Yeo-Jeong Jo"), "Tragikomödie, Drama", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_1)))
        itemList.add(CustomModel(2, "Joker", "Todd Phillips", listOf("Joaouin Phoenix","Robert de niro"), "Kriminalfilm, Superhelden-Film", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_2)))
        itemList.add(CustomModel(3, "Once upon a time ... in Hollywood", "Quentin Tarantino", listOf("Leonardo Dicaprio","Brad Pitt"), "Komödie", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_3)))
        itemList.add(CustomModel(4, "Midsommar", "Ari Aster", listOf("Florence Pugh","Jack Reynor"), "Horrorfilm", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_4)))
        itemList.add(CustomModel(5, "Systemsprenger", "Nora Fingscheidt", listOf("Helena Zengel","Gabriela Maria Schmeide"), "Drama", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_5)))
        itemList.add(CustomModel(6, "Avengers 4: Endgame", "Anthony Russo", listOf("Robert Downey Jr.","Chris Evans"), "Science Fiction", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_6)))
        itemList.add(CustomModel(7, "Wir", "Jordan Peele", listOf("Lupita Nyong´o","Winston Duke"), "Thriller", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_7)))
        itemList.add(CustomModel(8, "The Irishman", "Martin Scorsese", listOf("Robert De Niro","Al Pacino"), "Gangsterfilm", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_8)))
        itemList.add(CustomModel(9, "Ad Astra - zu den Sternen", "James Gray", listOf("Brad Pitt","Tommy Lee Jones"), "Science Fiction", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_9)))
        itemList.add(CustomModel(10, "John Wick: Kapitel 3", "Chad Stahelski", listOf("Keanu Reeves","Halle Berry"), "Actionfilm", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_10)))
        itemList.add(CustomModel(11, "Der Leuchtturm", "Robert Eggers", listOf("Robert Eggers","Robert Pattinson"), "Drama", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_11)))
        itemList.add(CustomModel(12, "Der goldene Handschuh", "Fatih Akin", listOf("Jonas Dassler","Marc Hosemann"), "Thriller", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_12)))
        itemList.add(CustomModel(13, "Spider-Man: Far from home", "Jon Watts", listOf("Tom Holland","Zendaya"), "Komödie", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_13)))
        itemList.add(CustomModel(14, "Rocketman", "Dexter Fletcher", listOf("Taron Egerton","Jamie Bell"), "Musikfilm", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_14)))
        itemList.add(CustomModel(15, "Shazam!", "David F. Sandberg", listOf("Zachary Levi","Mark Strong"), "Komödie", context.resources.getString(R.string.year_2019), context.resources.getString(R.string.plot_15)))
        adapter.setItems(itemList)

        liveAdapter.value = adapter
    }

    fun getAdapter() : LiveData<CustomAdapter>{
        return liveAdapter
    }
}
