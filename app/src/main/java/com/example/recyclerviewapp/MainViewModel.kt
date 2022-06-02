package com.example.recyclerviewapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel {

    private val customRepository : CustomRepository

    constructor() {
        customRepository = CustomRepository.getInstance()
    }

    public fun getItems() : List<CustomModel> {
        return customRepository.getItems()
    }

    public fun getFlowItems() : Flow<PagingData<CustomModel>> {
        return customRepository.getFlowItems().cachedIn(viewModelScope)
    }
}