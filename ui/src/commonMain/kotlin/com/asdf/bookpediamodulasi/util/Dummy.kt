package com.asdf.bookpediamodulasi.util

import com.asdf.bookpediamodulasi.services.data.model.Book

val books = (1 .. 100).map{
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://test.com",
        authors = listOf("Todo Carmel"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.67854,
        ratingsCount = 5,
        numPages = 100,
        numEditions = 3
    )
}