package navid.multiplash.feature.explore.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import navid.multiplash.feature.explore.ui.ExploreScreen

fun NavGraphBuilder.exploreGraph() {
    composable(
        route = ExploreRouter.PATH,
        arguments = ExploreRouter.arguments,
        deepLinks = ExploreRouter.deepLinks,
    ) {
        ExploreScreen()
    }
}
