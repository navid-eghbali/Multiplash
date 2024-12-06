package navid.multiplash.feature.details.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailsScreen(
    val photoId: String,
    val photoUrl: String,
)

fun NavGraphBuilder.detailsScreen(
    onNavigationIconClick: () -> Unit,
) {
    composable<DetailsScreen> {
        DetailsUi(
            args = it.toRoute(),
            onNavigationIconClick = onNavigationIconClick,
        )
    }
}

fun NavController.navigateToDetailsScreen(photoId: String, photoUrl: String) {
    navigate(DetailsScreen(photoId = photoId, photoUrl = photoUrl))
}
