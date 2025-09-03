package com.asdf.bookpediamodulasi.services.domain

import com.asdf.bookpediamodulasi.services.base.EmptyResult
import com.asdf.bookpediamodulasi.services.base.Result
import com.asdf.bookpediamodulasi.services.data.model.Book
import com.asdf.bookpediamodulasi.services.data.repository.BookRepository
import com.asdf.bookpediamodulasi.services.util.DataError
import kotlinx.coroutines.flow.Flow

class SearchBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(query: String): Result<List<Book>, DataError.Remote> =
        repository.searchBooks(query)
}

class GetBookDescriptionUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(
        bookId: String
    ): Result<String?, DataError> =
        repository.getBookDescription(bookId)
}

class GetFavoriteBooksUseCase(private val repository: BookRepository) {
    operator fun invoke(): Flow<List<Book>> =
        repository.getFavoriteBooks()
}

class IsBookFavoriteUseCase(private val repository: BookRepository) {
    operator fun invoke(id: String): Flow<Boolean> =
        repository.isBookFavorite(id)
}

class MarkAsFavoriteUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(book: Book): EmptyResult<DataError.Local> =
        repository.markAsFavorite(book)
}

class DeleteFromFavoritesUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: String) =
        repository.deleteFromFavorites(id)
}