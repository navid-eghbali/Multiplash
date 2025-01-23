package navid.multiplash.feature.user.ui

import androidx.compose.runtime.Immutable
import navid.multiplash.core.data.Photo

@Immutable
internal sealed interface UserState {
    data object Loading : UserState
    data class Error(val errorMessage: String) : UserState
    data class Success(
        val name: String,
        val username: String,
        val photos: List<Photo>,
        val profileImage: String,
        val bio: String?,
        val location: String?,
        val followingCount: String,
        val followersCount: String,
        val interests: List<String>,
        val totalDownloads: String,
        val totalViews: String,
    ) : UserState
}
