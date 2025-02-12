package navid.multiplash.feature.library.ui

import androidx.compose.runtime.Immutable
import navid.multiplash.feature.library.usecase.GetFavoriteUsersUseCase

@Immutable
internal sealed interface LibraryState {
    data object Loading : LibraryState

    data class Content(
        val favoriteUsers: List<GetFavoriteUsersUseCase.FavoriteUser> = emptyList(),
    ) : LibraryState
}
