package com.asdf.bookpediamodulasi.services.data.repository

import androidx.sqlite.SQLiteException
import com.asdf.bookpediamodulasi.services.base.EmptyResult
import com.asdf.bookpediamodulasi.services.base.Result
import com.asdf.bookpediamodulasi.services.base.map
import com.asdf.bookpediamodulasi.services.data.mapper.toBook
import com.asdf.bookpediamodulasi.services.data.mapper.toBookEntity
import com.asdf.bookpediamodulasi.services.data.model.Book
import com.asdf.bookpediamodulasi.services.data.source.local.FavoriteBookDao
import com.asdf.bookpediamodulasi.services.data.source.network.RemoteBookDataSource
import com.asdf.bookpediamodulasi.services.util.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val remoteDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String) : Result<String?, DataError>
    {
        val localResult = favoriteBookDao.getFavoriteBook(bookId)
        return if (localResult == null) {
            remoteDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>>{
        return favoriteBookDao
            .getFavoriteBook()
            .map { bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean>{
        return favoriteBookDao
            .getFavoriteBook()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>{
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }

}