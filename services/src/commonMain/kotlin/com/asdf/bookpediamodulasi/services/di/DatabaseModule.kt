package com.asdf.bookpediamodulasi.services.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.asdf.bookpediamodulasi.services.data.source.local.DatabaseFactory
import com.asdf.bookpediamodulasi.services.data.source.local.FavoriteBookDatabase
import org.koin.dsl.module

fun databaseModule() = module {
    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }

    single { get<FavoriteBookDatabase>().favoriteBookDao }
}