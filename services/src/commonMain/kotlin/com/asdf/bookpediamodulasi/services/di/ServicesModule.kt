package com.asdf.bookpediamodulasi.services.di

import org.koin.dsl.module

fun servicesModule() = module {
    includes(domainModule())
    includes(dataModule())
    includes(databaseModule())
}