package navid.multiplash.feature.search.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object SearchScreen

fun NavGraphBuilder.searchScreen(
    onPhotoClick: (String, String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    composable<SearchScreen> {
        SearchUi(
            onPhotoClick = onPhotoClick,
            onTopicClick = onTopicClick,
        )
    }
}
