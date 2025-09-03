package com.asdf.bookpediamodulasi.services.data.source.network

import com.asdf.bookpediamodulasi.services.base.Result
import com.asdf.bookpediamodulasi.services.data.model.SearchResponse
import com.asdf.bookpediamodulasi.services.data.repository.BookWork
import com.asdf.bookpediamodulasi.services.util.DataError

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponse, DataError.Remote>

    suspend fun getBookDetails(
        bookWorkId: String
    ): Result<BookWork, DataError.Remote>

}