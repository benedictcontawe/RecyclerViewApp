package com.example.recyclerviewapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory : ViewModelProvider.Factory {

    private lateinit var context: Context
    private lateinit var customListeners: CustomListeners

    constructor(context: Context, customListeners: CustomListeners) {
        this.context = context
        this.customListeners = customListeners
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(context, customListeners) as T
    }
}