package com.example.recyclerviewapp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class CustomRepository {

    companion object {
        @Volatile private var INSTANCE  : CustomRepository? = null

        fun getInstance() : CustomRepository {
            return INSTANCE ?: CustomRepository()
        }
    }

    public fun getItems() : List<CustomModel> {
        val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
        itemList.clear()
        itemList.add(CustomModel(R.drawable.ic_person_white, "A"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "B"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "C"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "D"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "E"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "F"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "G"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "H"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "I"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "J"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "K"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "L"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "M"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "N"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "O"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "P"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "Q"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "R"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "S"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "T"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "U"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "V"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "W"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "X"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "Y"))
        itemList.add(CustomModel(R.drawable.ic_person_white, "Z"))
        return itemList
    }

    public fun getFlowItems() : Flow<PagingData<CustomModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                CustomPagingSource(getItems())
            }
        ).flow
    }
}