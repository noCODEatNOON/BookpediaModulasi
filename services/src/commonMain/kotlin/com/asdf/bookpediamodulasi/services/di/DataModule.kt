package com.asdf.bookpediamodulasi.services.di

import com.asdf.bookpediamodulasi.services.data.repository.BookRepository
import com.asdf.bookpediamodulasi.services.data.repository.BookRepositoryImpl
import com.asdf.bookpediamodulasi.services.data.source.network.HttpClientFactory
import com.asdf.bookpediamodulasi.services.data.source.network.KtorRemoteBookDataSource
import com.asdf.bookpediamodulasi.services.data.source.network.RemoteBookDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun dataModule() = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::BookRepositoryImpl).bind<BookRepository>()
}