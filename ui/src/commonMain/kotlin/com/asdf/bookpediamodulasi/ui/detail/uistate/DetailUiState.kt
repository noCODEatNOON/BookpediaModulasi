package com.asdf.bookpediamodulasi.ui.detail.uistate

import com.asdf.bookpediamodulasi.services.data.model.Book

data class BookDetailUiState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null,
)
