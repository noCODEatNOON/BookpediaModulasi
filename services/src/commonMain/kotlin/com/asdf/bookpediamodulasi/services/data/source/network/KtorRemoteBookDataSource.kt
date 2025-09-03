package com.asdf.bookpediamodulasi.services.data.source.network

import com.asdf.bookpediamodulasi.services.base.Result
import com.asdf.bookpediamodulasi.services.data.model.SearchResponse
import com.asdf.bookpediamodulasi.services.data.repository.BookWork
import com.asdf.bookpediamodulasi.services.util.DataError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
    ): Result<SearchResponse, DataError.Remote> {
        return safeCall<SearchResponse> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookWork, DataError.Remote> {
        return safeCall<BookWork> {
            httpClient.get (
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }
}