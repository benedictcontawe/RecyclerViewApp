package com.example.recyclerviewapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList

class MainViewModel : AndroidViewModel {

    private lateinit var itemVerticalList : MutableList<CustomViewModel>
    private lateinit var itemHorizontalList : MutableList<CustomViewModel>
    private lateinit var liveList : MutableLiveData<PagedList<CustomModel>>

    constructor(application: Application) : super(application) {
        //customRepository = CustomRepository.getInstance(application)
        liveList = MutableLiveData()
    }

    public fun setItems() {
        itemHorizontalList = mutableListOf<CustomViewModel>()
        itemHorizontalList.clear()
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "1"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "2"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "3"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "4"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "5"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "6"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "7"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "8"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "9"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "10"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "11"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "12"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "13"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "14"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "15"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "16"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "17"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "18"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "19"))
        itemHorizontalList.add(CustomViewModel(R.drawable.ic_person_white, "20"))

        itemVerticalList = mutableListOf<CustomViewModel>()
        itemVerticalList.clear()
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "A"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "B"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "C"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "D"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "E"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "F"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "G"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "H"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "I"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "J"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "K"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "L"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "M"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "N"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "O"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "P"))
        itemVerticalList.add(CustomViewModel(R.drawable.ic_person_white, "Q"))
    }

    public fun getVerticalList() : List<CustomViewModel> {
        return itemVerticalList ?: emptyList<CustomViewModel>()
    }

    public fun getHorizontalList() : List<CustomViewModel> {
        return itemHorizontalList ?: emptyList<CustomViewModel>()
    }
}