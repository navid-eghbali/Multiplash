package navid.multiplash.feature.photos.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class PhotosScreen(
    val query: String,
)

fun NavGraphBuilder.photosScreen(
    onNavigationIconClick: () -> Unit,
    onPhotoClick: (String, String) -> Unit,
) {
    composable<PhotosScreen> {
        PhotosUi(
            args = it.toRoute(),
            onNavigationIconClick = onNavigationIconClick,
            onPhotoClick = onPhotoClick,
        )
    }
}

fun NavController.navigateToPhotosScreen(query: String) {
    navigate(PhotosScreen(query = query))
}
