package com.asdf.bookpediamodulasi.di

import com.asdf.bookpediamodulasi.ui.SelectedBookViewModel
import com.asdf.bookpediamodulasi.ui.detail.viewmodel.DetailViewModel
import com.asdf.bookpediamodulasi.ui.home.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun uiModule() = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}