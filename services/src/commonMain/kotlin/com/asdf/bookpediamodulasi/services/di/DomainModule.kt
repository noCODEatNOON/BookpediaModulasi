package com.asdf.bookpediamodulasi.services.di

import com.asdf.bookpediamodulasi.services.domain.DeleteFromFavoritesUseCase
import com.asdf.bookpediamodulasi.services.domain.GetBookDescriptionUseCase
import com.asdf.bookpediamodulasi.services.domain.GetFavoriteBooksUseCase
import com.asdf.bookpediamodulasi.services.domain.IsBookFavoriteUseCase
import com.asdf.bookpediamodulasi.services.domain.MarkAsFavoriteUseCase
import com.asdf.bookpediamodulasi.services.domain.SearchBooksUseCase
import org.koin.dsl.module

fun domainModule() = module {
    single { SearchBooksUseCase(get()) }
    single { GetBookDescriptionUseCase(get()) }
    single { GetFavoriteBooksUseCase(get()) }
    single { IsBookFavoriteUseCase(get()) }
    single { MarkAsFavoriteUseCase(get()) }
    single { DeleteFromFavoritesUseCase(get()) }
}