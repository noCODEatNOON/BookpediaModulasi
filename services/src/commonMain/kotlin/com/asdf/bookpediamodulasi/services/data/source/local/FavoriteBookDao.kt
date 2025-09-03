package com.asdf.bookpediamodulasi.services.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Upsert
    suspend fun upsert(book: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBook(): Flow<List<BookEntity>>

    @Query("SELECT * FROM bookentity WHERE id = :id")
    suspend fun getFavoriteBook(id: String): BookEntity?

    @Query("DELETE FROM bookentity WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String)
}