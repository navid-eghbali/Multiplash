package navid.multiplash.ui.home

import navid.multiplash.core.api.data.Photo

sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Error(val error: String) : HomeUiState

    data class Success(
        val items: List<Photo>,
    ) : HomeUiState
}
