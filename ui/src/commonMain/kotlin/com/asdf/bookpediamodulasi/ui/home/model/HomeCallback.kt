package com.asdf.bookpediamodulasi.ui.home.model

import com.asdf.bookpediamodulasi.services.data.model.Book

sealed interface HomeCallback {
    data class OnSearchQueryChange(val query: String): HomeCallback
    data class OnBookClick(val book: Book): HomeCallback
    data class OnTabSelected(val index: Int): HomeCallback
}