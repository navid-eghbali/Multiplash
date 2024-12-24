package navid.multiplash.feature.explore.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ExploreScreen

fun NavGraphBuilder.exploreScreen(
    onPhotoClick: (String, String) -> Unit,
) {
    composable<ExploreScreen> {
        ExploreUi(
            onPhotoClick = onPhotoClick,
        )
    }
}
