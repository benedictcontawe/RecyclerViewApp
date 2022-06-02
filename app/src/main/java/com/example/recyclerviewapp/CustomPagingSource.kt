package com.example.recyclerviewapp

import androidx.paging.PagingSource
import androidx.paging.PagingState

class CustomPagingSource : PagingSource<Int ,CustomModel>{

    companion object {
        private const val TMDB_STARTING_PAGE_INDEX = 1
    }

    private val model : List<CustomModel>

    constructor(model : List<CustomModel>) {
        this.model = model
    }

    override suspend fun load(params : LoadParams<Int>) : LoadResult<Int, CustomModel> {
        val pageIndex : Int = params.key ?: TMDB_STARTING_PAGE_INDEX
        params.loadSize
        return try {
            LoadResult.Page (
                data = model,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = null
            )
        } catch (exception : Exception) {
            LoadResult.Error(exception)
        }
    }
    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state : PagingState<Int, CustomModel>) : Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}