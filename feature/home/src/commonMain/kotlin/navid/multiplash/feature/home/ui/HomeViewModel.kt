package navid.multiplash.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import navid.multiplash.feature.home.data.remote.HomeClient
import navid.multiplash.feature.home.data.repository.PhotosPagingSource

class HomeViewModel(
    private val homeClient: HomeClient,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val state = _state.asStateFlow()

    init {
        _state.update {
            HomeUiState.Success(
                photosPager = Pager(PagingConfig(pageSize = 30)) {
                    PhotosPagingSource(homeClient)
                }.flow.cachedIn(viewModelScope)
            )
        }
        /*viewModelScope.launch {
            getPhotosUseCase()
                .onSuccess { result -> _state.update { HomeUiState.Success(result) } }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    _state.update { HomeUiState.Error(throwable.message.toString()) }
                }
        }*/
    }

}
