package navid.multiplash.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.details.usecase.DownloadPhotoUseCase
import navid.multiplash.feature.details.usecase.GetPhotoUseCase

internal class DetailsViewModel(
    args: DetailsScreen,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
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

    fun onDownloadClick(photoId: String, url: String) {
        viewModelScope.launch {
            _state.update { it.copy(isDownloading = true) }
            downloadPhotoUseCase(photoId, url)
                .onSuccess { path ->
                    println("File successfully saved to $path")
                    _state.update { it.copy(isDownloading = false) }
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    _state.update { it.copy(isDownloading = false) }
                }
        }
    }

    private fun fetchPhoto(photoId: String) {
        viewModelScope.launch {
            getPhotoUseCase(photoId)
                .onSuccess { photo -> _state.update { it.copy(photo = photo) } }
                .onFailure { throwable -> throwable.printStackTrace() }
        }
    }
}
