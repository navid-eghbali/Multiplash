package navid.multiplash.feature.topic.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class TopicScreen(val id: String)

fun NavGraphBuilder.topicScreen(
    onNavigationIconClick: () -> Unit,
    onUserClick: (String) -> Unit,
    onPhotoClick: (String) -> Unit,
) {
    composable<TopicScreen> {
        TopicUi(
            args = it.toRoute(),
            onNavigationIconClick = onNavigationIconClick,
            onUserClick = onUserClick,
            onPhotoClick = onPhotoClick,
        )
    }
}

fun NavController.navigateToTopicScreen(topicId: String) {
    navigate(TopicScreen(id = topicId))
}
