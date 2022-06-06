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

        public const val DEFAULT_PAGE_INDEX = 0
        public const val DEFAULT_PAGE_SIZE = 30
    }

    public fun getItems() : List<CustomModel> {
        val itemList : MutableList<CustomModel> = mutableListOf<CustomModel>()
        itemList.clear()
        /*
        itemList.add(CustomModel(0, R.drawable.ic_person_white, "A"))
        itemList.add(CustomModel(1, R.drawable.ic_person_white, "B"))
        itemList.add(CustomModel(2, R.drawable.ic_person_white, "C"))
        itemList.add(CustomModel(3, R.drawable.ic_person_white, "D"))
        itemList.add(CustomModel(4, R.drawable.ic_person_white, "E"))
        itemList.add(CustomModel(5, R.drawable.ic_person_white, "F"))
        itemList.add(CustomModel(6, R.drawable.ic_person_white, "G"))
        itemList.add(CustomModel(7, R.drawable.ic_person_white, "H"))
        itemList.add(CustomModel(8, R.drawable.ic_person_white, "I"))
        itemList.add(CustomModel(9, R.drawable.ic_person_white, "J"))
        itemList.add(CustomModel(10, R.drawable.ic_person_white, "K"))
        itemList.add(CustomModel(11, R.drawable.ic_person_white, "L"))
        itemList.add(CustomModel(12, R.drawable.ic_person_white, "M"))
        itemList.add(CustomModel(13, R.drawable.ic_person_white, "N"))
        itemList.add(CustomModel(14, R.drawable.ic_person_white, "O"))
        itemList.add(CustomModel(15, R.drawable.ic_person_white, "P"))
        itemList.add(CustomModel(16, R.drawable.ic_person_white, "Q"))
        itemList.add(CustomModel(17, R.drawable.ic_person_white, "R"))
        itemList.add(CustomModel(18, R.drawable.ic_person_white, "S"))
        itemList.add(CustomModel(19, R.drawable.ic_person_white, "T"))
        itemList.add(CustomModel(20, R.drawable.ic_person_white, "U"))
        itemList.add(CustomModel(21, R.drawable.ic_person_white, "V"))
        itemList.add(CustomModel(22, R.drawable.ic_person_white, "W"))
        itemList.add(CustomModel(23, R.drawable.ic_person_white, "X"))
        itemList.add(CustomModel(24, R.drawable.ic_person_white, "Y"))
        itemList.add(CustomModel(25, R.drawable.ic_person_white, "Z"))
        */
        for (index in 0 until 500) {
            itemList.add(CustomModel(index, R.drawable.ic_person_white, "$index"))
        }
        return itemList
    }

    public fun getFlowItems() : Flow<PagingData<CustomModel>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { CustomPagingSource(getItems()) }
        ).flow
    }

    private fun getDefaultPageConfig() : PagingConfig {
        return PagingConfig(
            enablePlaceholders = false,
            initialLoadSize = 10,
            maxSize = 499,
            prefetchDistance = 1,
            pageSize = 10
        )
    }
}