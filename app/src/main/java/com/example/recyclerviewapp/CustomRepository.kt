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
        for (index in 0 until 200) {
            itemList.add(CustomModel(index, R.drawable.ic_person_white, "$index"))
        }
        itemList.filter { it.id < 11 }.map { it.setPage(0) }
        itemList.filter { it.id > 10 && it.id < 21 }.map { it.setPage(1) }
        itemList.filter { it.id > 20 && it.id < 31 }.map { it.setPage(2) }
        itemList.filter { it.id > 30 && it.id < 41 }.map { it.setPage(3) }
        itemList.filter { it.id > 40 && it.id < 51 }.map { it.setPage(4) }
        itemList.filter { it.id > 50 && it.id < 61 }.map { it.setPage(5) }
        itemList.filter { it.id > 60 && it.id < 71 }.map { it.setPage(6) }
        itemList.filter { it.id > 70 && it.id < 81 }.map { it.setPage(7) }
        itemList.filter { it.id > 80 && it.id < 91 }.map { it.setPage(8) }
        itemList.filter { it.id > 90 && it.id < 101 }.map { it.setPage(9) }
        itemList.filter { it.id > 100 && it.id < 111 }.map { it.setPage(10) }
        itemList.filter { it.id > 110 && it.id < 121 }.map { it.setPage(11) }
        itemList.filter { it.id > 120 && it.id < 131 }.map { it.setPage(12) }
        itemList.filter { it.id > 130 && it.id < 141 }.map { it.setPage(13) }
        itemList.filter { it.id > 140 && it.id < 151 }.map { it.setPage(14) }
        itemList.filter { it.id > 150 && it.id < 161 }.map { it.setPage(15) }
        itemList.filter { it.id > 160 && it.id < 171 }.map { it.setPage(16) }
        itemList.filter { it.id > 170 && it.id < 181 }.map { it.setPage(17) }
        itemList.filter { it.id > 180 && it.id < 191 }.map { it.setPage(18) }
        itemList.filter { it.id > 190 && it.id < 201 }.map { it.setPage(19) }
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
            maxSize = Int.MAX_VALUE ?: PagingConfig.MAX_SIZE_UNBOUNDED,
            prefetchDistance = 1,
            pageSize = 10,
            jumpThreshold = Int.MIN_VALUE
        )
    }
}