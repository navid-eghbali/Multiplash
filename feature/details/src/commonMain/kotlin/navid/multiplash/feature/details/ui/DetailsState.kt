package navid.multiplash.feature.details.ui

import androidx.compose.runtime.Immutable
import navid.multiplash.feature.details.usecase.GetPhotoUseCase

@Immutable
internal data class DetailsState(
    val url: String,
    val photo: GetPhotoUseCase.Photo? = null,
    val isLoading: Boolean = false,
)
