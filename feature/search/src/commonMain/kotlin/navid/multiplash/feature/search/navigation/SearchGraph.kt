package navid.multiplash.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import navid.multiplash.feature.search.ui.SearchScreen


fun NavGraphBuilder.searchGraph() {
    composable(
        route = SearchRouter.PATH,
        arguments = SearchRouter.arguments,
        deepLinks = SearchRouter.deepLinks,
    ) {
        SearchScreen()
    }
}
