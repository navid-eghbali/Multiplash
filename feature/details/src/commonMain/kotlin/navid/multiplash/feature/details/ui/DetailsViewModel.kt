package navid.multiplash.feature.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.details.usecase.BookmarkPhotoUseCase
import navid.multiplash.feature.details.usecase.DownloadPhotoUseCase
import navid.multiplash.feature.details.usecase.GetPhotoUseCase
import navid.multiplash.feature.details.usecase.IsPhotoBookmarkedUseCase
import navid.multiplash.feature.details.usecase.UnbookmarkPhotoUseCase

internal class DetailsViewModel(
    args: DetailsScreen,
    isPhotoBookmarkedUseCase: IsPhotoBookmarkedUseCase,
    private val getPhotoUseCase: GetPhotoUseCase,
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
    private val bookmarkPhotoUseCase: BookmarkPhotoUseCase,
    private val unbookmarkPhotoUseCase: UnbookmarkPhotoUseCase,
) : ViewModel() {

    private val _events = Channel<Event>(Channel.CONFLATED)
    val events = _events.receiveAsFlow()

    private val _state = MutableStateFlow(DetailsState(url = args.photoUrl))
    val state = _state
        .onStart { fetchPhoto(args.photoId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = DetailsState(url = args.photoUrl)
        )

    init {
        isPhotoBookmarkedUseCase(args.photoId)
            .onEach { bookmarked -> _state.update { it.copy(isBookmarked = bookmarked) } }
            .launchIn(viewModelScope)
    }

    fun onImageLoading() {
        _state.update { it.copy(isLoading = true) }
    }

    fun onImageComplete() {
        _state.update { it.copy(isLoading = false) }
    }

    fun onBookmarkClick(photoId: String, url: String) {
        viewModelScope.launch {
            if (_state.value.isBookmarked) {
                unbookmarkPhotoUseCase(photoId)
            } else {
                bookmarkPhotoUseCase(photoId, url)
            }
        }
    }

    fun onSaveClick(photoId: String, url: String) {
        viewModelScope.launch {
            _state.update { it.copy(isDownloading = true) }
            downloadPhotoUseCase(photoId, url)
                .onSuccess { path ->
                    println("File successfully saved to $path")
                    _state.update { it.copy(isDownloading = false) }
                    _events.trySend(Event.SavePhotoSucceed)
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    _state.update { it.copy(isDownloading = false) }
                    _events.trySend(Event.SavePhotoFailed)
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

    sealed interface Event {
        data object SavePhotoSucceed : Event
        data object SavePhotoFailed : Event
    }
}
