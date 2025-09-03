package com.asdf.bookpediamodulasi.services.data.repository

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkSerializer::class)
data class BookWork(
    val description: String? = null
)
