package com.pinedeveloper.homework.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pinedeveloper.homework.entity.RickAndMorty

class RickPagingSource(val rickUseCase: GetRickUseCase,val throwable: MutableLiveData<Throwable?>): PagingSource<Int,com.pinedeveloper.homework.entity.Result>() {
    override fun getRefreshKey(state: PagingState<Int, com.pinedeveloper.homework.entity.Result>): Int? = FIRST_PAGE

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, com.pinedeveloper.homework.entity.Result> {
        val page: Int = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            rickUseCase.execute(page)
        }.fold(
            onSuccess = {
                PagingSource.LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = if(it.results.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                throwable.postValue(it)
                PagingSource.LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}