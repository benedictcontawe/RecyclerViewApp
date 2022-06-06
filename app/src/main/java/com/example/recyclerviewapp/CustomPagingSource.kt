package com.example.recyclerviewapp

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState

class CustomPagingSource : PagingSource<Int ,CustomModel>{

    companion object {
        private var TAG : String = CustomPagingSource::class.java.getSimpleName()
    }

    private val model : List<CustomModel>

    constructor(model : List<CustomModel>) {
        this.model = model
    }

    override suspend fun load(params : LoadParams<Int>) : LoadResult<Int, CustomModel> {
        Log.d(TAG, "load key ${params.key} ${params.loadSize}")
        val pageIndex : Int = params.key ?: CustomRepository.DEFAULT_PAGE_INDEX
        return try {
            LoadResult.Page (
                data = model.orEmpty<CustomModel>(),
                prevKey = if (pageIndex == CustomRepository.DEFAULT_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (pageIndex == CustomRepository.DEFAULT_PAGE_INDEX || model.isNullOrEmpty()) null else pageIndex + 1
            )
        } catch (exception : Exception) {
            //LoadResult.Invalid()
            LoadResult.Error(exception)
        }
    }
    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    @ExperimentalPagingApi
    override fun getRefreshKey(state : PagingState<Int, CustomModel>) : Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}