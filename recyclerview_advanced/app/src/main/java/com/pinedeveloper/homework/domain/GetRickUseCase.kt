package com.pinedeveloper.homework.domain

import com.pinedeveloper.homework.data.RickRepository
import com.pinedeveloper.homework.entity.RickAndMorty
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetRickUseCase @Inject constructor(val rickCase: RickRepository)  {
    suspend fun execute(page:Int): RickAndMorty {
        delay(2000)
        return rickCase.getListRickAndMorty(page)
    }
}