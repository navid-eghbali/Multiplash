package navid.multiplash.feature.topic.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class TopicScreen(val id: String)

fun NavGraphBuilder.topicScreen(
    onNavigationIconClick: () -> Unit,
) {
    composable<TopicScreen> {
        TopicUi(
            args = it.toRoute(),
            onNavigationIconClick = onNavigationIconClick,
        )
    }
}
