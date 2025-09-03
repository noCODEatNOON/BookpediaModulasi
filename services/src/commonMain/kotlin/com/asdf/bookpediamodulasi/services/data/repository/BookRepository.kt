package com.asdf.bookpediamodulasi.services.data.repository

import com.asdf.bookpediamodulasi.services.base.Result
import com.asdf.bookpediamodulasi.services.base.EmptyResult
import com.asdf.bookpediamodulasi.services.data.model.Book
import com.asdf.bookpediamodulasi.services.util.DataError
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>

    fun getFavoriteBooks()
    : Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}