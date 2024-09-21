package navid.multiplash.feature.details.ui

import androidx.compose.runtime.Immutable

@Immutable
internal data class DetailsState(
    val url: String,
    val isLoading: Boolean = false,
)
