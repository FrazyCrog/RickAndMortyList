package com.pinedeveloper.homework.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pinedeveloper.homework.domain.GetRickUseCase
import com.pinedeveloper.homework.domain.RickPagingSource
import com.pinedeveloper.homework.entity.Result
import com.pinedeveloper.homework.entity.RickAndMorty
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainViewModel @Inject constructor(val rickUseCase: GetRickUseCase): ViewModel() {
    val pagedRickAndMorty : Flow<PagingData<Result>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {RickPagingSource(rickUseCase,throwable)}
    ).flow.cachedIn(viewModelScope)

    val throwable = MutableLiveData<Throwable?>(null)
}