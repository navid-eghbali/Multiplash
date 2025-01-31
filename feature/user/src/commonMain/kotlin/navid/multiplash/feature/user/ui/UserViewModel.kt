package navid.multiplash.feature.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.user.usecase.GetUserPhotosUseCase
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCase
import navid.multiplash.feature.user.usecase.GetUserUseCase

internal class UserViewModel(
    getUserPhotosUseCase: GetUserPhotosUseCase,
    private val args: UserScreen,
    private val getUserUseCase: GetUserUseCase,
    private val getUserStatisticsUseCase: GetUserStatisticsUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<UserState> = MutableStateFlow(UserState())
    val state = _state
        .onStart {
            fetchUser(username = args.username)
            fetchUserStatistics(username = args.username)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UserState(),
        )

    val pagedPhotos = getUserPhotosUseCase(args.username)
        .cachedIn(viewModelScope)

    fun onReload() {
        fetchUser(username = args.username)
        // fetchUserStatistics(username = args.username)
    }

    fun onReloadStats() {
        fetchUserStatistics(username = args.username)
    }

    private fun fetchUser(username: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getUserUseCase(username)
                .onSuccess { user -> _state.update { it.copy(isLoading = false, user = user) } }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    _state.update { it.copy(isLoading = false, errorMessage = throwable.message) }
                }
        }
    }

    private fun fetchUserStatistics(username: String) {
        viewModelScope.launch {
            getUserStatisticsUseCase(username)
                .onSuccess { stats -> _state.update { it.copy(stats = stats) } }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    _state.update { it.copy(stats = GetUserStatisticsUseCase.Stats("", "")) }
                }
        }
    }
}
