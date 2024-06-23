package navid.multiplash.feature.home.ui

import androidx.compose.runtime.Immutable
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.feature.home.data.model.Photo

@Immutable
sealed interface HomeUiState {

    @Immutable
    data object Loading : HomeUiState

    @Immutable
    data class Error(val error: String) : HomeUiState

    @Immutable
    data class Success(
        val photosPager: Flow<PagingData<Photo>>,
    ) : HomeUiState
}
