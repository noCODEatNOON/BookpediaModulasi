package com.asdf.bookpediamodulasi.ui.detail.model

import com.asdf.bookpediamodulasi.services.data.model.Book

sealed interface BookDetailCallback {
    data object OnBackClick : BookDetailCallback
    data object OnFavoriteClick : BookDetailCallback
    data class OnSelectedBookChange(val book: Book): BookDetailCallback
}