package navid.multiplash.feature.home.ui

import navid.multiplash.feature.home.data.model.Photo

sealed interface HomeUiState {

    data object Loading : HomeUiState

    data class Error(val error: String) : HomeUiState

    data class Success(
        val items: List<Photo>,
    ) : HomeUiState
}
