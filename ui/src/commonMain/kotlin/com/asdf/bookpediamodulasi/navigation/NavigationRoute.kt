package com.asdf.bookpediamodulasi.navigation

sealed class NavigationRoute(val route: String) {
    data object BookGraph : NavigationRoute("book_graph")
    data object BookList : NavigationRoute("book_list")

    data class BookDetail(val bookId: String) : NavigationRoute("book_detail/$bookId") {
        companion object {
            fun routeWithArg() = "book_detail/{bookId}"
        }
    }
}