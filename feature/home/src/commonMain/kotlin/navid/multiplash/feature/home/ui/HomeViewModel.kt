package navid.multiplash.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.home.data.repository.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val state = _state.asStateFlow()

    init {
        println("BOOGH> $this")
        viewModelScope.launch {
            homeRepository.getPhotos().fold(
                onSuccess = { result -> _state.update { HomeUiState.Success(result.photos) } },
                onFailure = { throwable ->
                    throwable.printStackTrace()
                    HomeUiState.Error(throwable.message.toString())
                },
            )
        }
    }

}
