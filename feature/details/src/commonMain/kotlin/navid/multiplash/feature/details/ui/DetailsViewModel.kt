package navid.multiplash.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.details.usecase.GetPhotoUseCase

internal class DetailsViewModel(
    args: DetailsScreen,
    private val getPhotoUseCase: GetPhotoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState(url = args.photoUrl))
    val state = _state
        .onStart { fetchPhoto(args.photoId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = DetailsState(url = args.photoUrl)
        )

    fun onImageLoading() {
        _state.update { it.copy(isLoading = true) }
    }

    fun onImageComplete() {
        _state.update { it.copy(isLoading = false) }
    }

    private fun fetchPhoto(photoId: String) {
        viewModelScope.launch {
            getPhotoUseCase(photoId).fold(
                onSuccess = { photo ->
                    println(photo)
                    _state.update { it.copy(photo = photo) }
                },
                onFailure = { throwable ->
                    println(throwable.printStackTrace())
                },
            )
        }
    }
}
