package com.asdf.bookpediamodulasi.ui.home.uistate

import com.asdf.bookpediamodulasi.services.data.model.Book
import com.asdf.bookpediamodulasi.util.UiText
import com.asdf.bookpediamodulasi.util.books

data class HomeUiState(
    val searchQuery: String = "Mein Kampf",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
