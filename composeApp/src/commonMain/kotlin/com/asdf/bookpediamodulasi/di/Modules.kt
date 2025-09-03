package com.asdf.bookpediamodulasi.di

import com.asdf.bookpediamodulasi.services.di.servicesModule
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(servicesModule())
    includes(uiModule())
}
