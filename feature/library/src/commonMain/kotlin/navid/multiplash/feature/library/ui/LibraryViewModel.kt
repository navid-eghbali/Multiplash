package navid.multiplash.feature.library.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.library.usecase.GetBookmarksUseCase
import navid.multiplash.feature.library.usecase.GetFavoriteUsersUseCase

internal class LibraryViewModel(
    getBookmarksUseCase: GetBookmarksUseCase,
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<LibraryState> = MutableStateFlow(LibraryState.Loading)
    val state = _state
        .onStart { fetchFavoriteUsers() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LibraryState.Loading,
        )
    val pagedBookmarks = getBookmarksUseCase()
        .cachedIn(viewModelScope)

    private fun fetchFavoriteUsers() {
        viewModelScope.launch {
            _state.update { LibraryState.Loading }
            getFavoriteUsersUseCase()
                .onSuccess { favoriteUsers ->
                    _state.update { LibraryState.Content(favoriteUsers = favoriteUsers) }
                }
                .onFailure {
                    it.printStackTrace()
                    _state.update { LibraryState.Content(favoriteUsers = emptyList()) }
                }
        }
    }
}
