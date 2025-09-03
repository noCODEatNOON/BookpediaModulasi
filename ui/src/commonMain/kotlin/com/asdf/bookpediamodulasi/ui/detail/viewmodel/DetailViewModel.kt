package com.asdf.bookpediamodulasi.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asdf.bookpediamodulasi.services.base.onSuccess
import com.asdf.bookpediamodulasi.services.domain.DeleteFromFavoritesUseCase
import com.asdf.bookpediamodulasi.services.domain.GetBookDescriptionUseCase
import com.asdf.bookpediamodulasi.services.domain.IsBookFavoriteUseCase
import com.asdf.bookpediamodulasi.services.domain.MarkAsFavoriteUseCase
import com.asdf.bookpediamodulasi.ui.detail.model.BookDetailCallback
import com.asdf.bookpediamodulasi.ui.detail.uistate.BookDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val deleteFromFavoritesUseCase: DeleteFromFavoritesUseCase,
    private val markAsFavoriteUseCase: MarkAsFavoriteUseCase,
    private val isBookFavoriteUseCase: IsBookFavoriteUseCase,
    private val getBookDescriptionUseCase: GetBookDescriptionUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val bookId: String = checkNotNull(savedStateHandle["bookId"])
    private val _state = MutableStateFlow(BookDetailUiState())
    val state = _state
        .onStart {
            fetchBookDescription()
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookDetailCallback) {
        when(action) {
            is BookDetailCallback.OnSelectedBookChange -> {
                _state.update { it.copy(
                    book = action.book
                ) }
            }
            is BookDetailCallback.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        deleteFromFavoritesUseCase(bookId)
                    } else {
                        state.value.book?.let { book ->
                            markAsFavoriteUseCase(book)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    private fun observeFavoriteStatus() {
        isBookFavoriteUseCase(bookId)
            .onEach { isFavorite ->
                _state.update { it.copy(
                    isFavorite = isFavorite
                )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            getBookDescriptionUseCase(bookId)
                .onSuccess { description ->
                    _state.update { it.copy(
                        book = it.book?.copy(
                            description = description
                        ),
                        isLoading = false
                    ) }
                }

        }
    }
}