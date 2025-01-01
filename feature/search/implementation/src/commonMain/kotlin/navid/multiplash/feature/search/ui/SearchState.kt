package navid.multiplash.feature.search.ui

import androidx.compose.runtime.Immutable
import navid.multiplash.feature.search.usecase.GetTopicsUseCase

@Immutable
internal data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val topics: List<GetTopicsUseCase.Topic> = emptyList(),
)
