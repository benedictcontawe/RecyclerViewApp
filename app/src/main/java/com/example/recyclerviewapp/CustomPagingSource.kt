package com.example.recyclerviewapp

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState

class CustomPagingSource : PagingSource<Int ,CustomModel> {

    companion object {
        private val TAG : String = CustomPagingSource::class.java.getSimpleName()
    }

    private val model : List<CustomModel>

    constructor(model : List<CustomModel>) {
        this.model = model
    }

    override suspend fun load(params : LoadParams<Int>) : LoadResult<Int, CustomModel> {
        Log.d(TAG, "load key ${params.key} ${params.loadSize}")
        val pageIndex : Int = params.key ?: CustomRepository.DEFAULT_PAGE_INDEX
        return try {
            Log.d(TAG, "getData($pageIndex) : ${getData(pageIndex).size}")
            getData(pageIndex).map {
                Log.d(TAG, "getData($pageIndex) : ${it}")
            }
            LoadResult.Page (
                data = getData(pageIndex),
                prevKey = if (pageIndex == CustomRepository.DEFAULT_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (getData(pageIndex).isNullOrEmpty()) null else pageIndex + 1
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

    private fun getData(index : Int) : List<CustomModel> {
        return if (model.filter { it.getPage() == index }.isNullOrEmpty()) emptyList<CustomModel>()
        else model.filter { it.getPage() == index }
    }
}