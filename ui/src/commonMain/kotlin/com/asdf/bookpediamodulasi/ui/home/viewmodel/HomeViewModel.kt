package com.asdf.bookpediamodulasi.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asdf.bookpediamodulasi.services.base.onError
import com.asdf.bookpediamodulasi.services.base.onSuccess
import com.asdf.bookpediamodulasi.services.data.model.Book
import com.asdf.bookpediamodulasi.services.domain.GetFavoriteBooksUseCase
import com.asdf.bookpediamodulasi.services.domain.SearchBooksUseCase
import com.asdf.bookpediamodulasi.ui.home.model.HomeCallback
import com.asdf.bookpediamodulasi.ui.home.uistate.HomeUiState
import com.asdf.bookpediamodulasi.util.toUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase,
    private val searchBooksUseCase: SearchBooksUseCase
): ViewModel() {
    private var cacheBooks = emptyList<Book>()
    private var searchJob: Job? = null
    private var observeFavoriteBooksJob: Job? = null

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        observeSearchQuery()
        observeFavoriteBooks()
    }

    fun onAction(action: HomeCallback) {
        when (action) {
            is HomeCallback.OnBookClick -> {

            }
            is HomeCallback.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is HomeCallback.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun observeFavoriteBooks() {
        observeFavoriteBooksJob?.cancel()
        observeFavoriteBooksJob = getFavoriteBooksUseCase()
            .onEach { favoriteBooks ->
                _state.update { it.copy(favoriteBooks = favoriteBooks) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cacheBooks
                            )
                        }
                    }
                    query.length > 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }

        searchBooksUseCase(query)
            .onSuccess { searchResults ->
                cacheBooks = searchResults
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}