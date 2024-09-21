package navid.multiplash.feature.explore.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import navid.multiplash.feature.explore.ui.ExploreScreen
import navid.multiplash.feature.explore.ui.ExploreUi

fun NavGraphBuilder.exploreScreen(
    onItemClick: (String) -> Unit,
) {
    composable<ExploreScreen> {
        ExploreUi(
            onItemClick = onItemClick,
        )
    }
}
