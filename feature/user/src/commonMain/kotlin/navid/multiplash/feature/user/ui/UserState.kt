package navid.multiplash.feature.user.ui

import androidx.compose.runtime.Immutable
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCase
import navid.multiplash.feature.user.usecase.GetUserUseCase

@Immutable
internal data class UserState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavorited: Boolean = false,
    val user: GetUserUseCase.User? = null,
    val stats: GetUserStatisticsUseCase.Stats? = null,
)
