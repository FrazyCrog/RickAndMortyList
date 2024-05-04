package com.pinedeveloper.homework.di

import com.pinedeveloper.homework.presentation.MainViewModelFactory
import dagger.Component

@Component
interface AppComponent {
    fun mainViewModelFactory(): MainViewModelFactory
}