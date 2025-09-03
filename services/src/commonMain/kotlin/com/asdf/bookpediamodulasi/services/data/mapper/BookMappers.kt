package com.asdf.bookpediamodulasi.services.data.mapper

import com.asdf.bookpediamodulasi.services.data.model.Book
import com.asdf.bookpediamodulasi.services.data.model.SearchedBook
import com.asdf.bookpediamodulasi.services.data.source.local.BookEntity

fun SearchedBook.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-M.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-M.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        ratingsAverage = averageRating,
        ratingsCount = ratingsCount,
        numPages = numPages,
        numEditions = numEditions
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numPages = numPages,
        numEditions = numEditions
    )
}