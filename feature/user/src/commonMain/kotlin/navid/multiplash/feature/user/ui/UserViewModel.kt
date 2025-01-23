package navid.multiplash.feature.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import navid.multiplash.feature.user.usecase.GetUserPhotosUseCase
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCase
import navid.multiplash.feature.user.usecase.GetUserUseCase

internal class UserViewModel(
    args: UserScreen,
    getUserPhotosUseCase: GetUserPhotosUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserStatisticsUseCase: GetUserStatisticsUseCase,
) : ViewModel() {

    val state: StateFlow<UserState> = combine(
        fetchUser(args.username),
        fetchUserStatistics(args.username),
        transform = { user, stats ->
            when {
                user != null && stats != null -> UserState.Success(
                    name = user.name,
                    username = user.username,
                    photos = user.photos,
                    profileImage = user.profileImage,
                    bio = user.bio,
                    location = user.location,
                    followingCount = user.followingCount,
                    followersCount = user.followersCount,
                    interests = user.interests,
                    totalDownloads = stats.totalDownloads,
                    totalViews = stats.totalViews,
                )

                else -> UserState.Loading
            }
        }
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = UserState.Loading,
    )

    val pagedPhotos = getUserPhotosUseCase(args.username)
        .cachedIn(viewModelScope)

    fun onReload() {

    }

    private fun fetchUser(username: String) = flow {
        getUserUseCase(username).fold(
            onSuccess = { emit(it) },
            onFailure = { throwable ->
                println(throwable.printStackTrace())
                emit(null)
            },
        )
    }

    private fun fetchUserStatistics(username: String) = flow {
        getUserStatisticsUseCase(username).fold(
            onSuccess = { emit(it) },
            onFailure = { throwable ->
                println(throwable.printStackTrace())
                emit(null)
            }
        )
    }
}
