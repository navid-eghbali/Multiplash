package navid.multiplash.shared.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import navid.multiplash.feature.details.navigation.detailsScreen
import navid.multiplash.feature.details.ui.DetailsScreen
import navid.multiplash.feature.explore.navigation.exploreScreen
import navid.multiplash.feature.explore.ui.ExploreScreen
import navid.multiplash.feature.library.navigation.libraryScreen
import navid.multiplash.feature.library.ui.LibraryScreen
import navid.multiplash.feature.search.navigation.searchScreen
import navid.multiplash.feature.search.ui.SearchScreen
import navid.multiplash.shared.navigation.ExploreGraph
import navid.multiplash.shared.navigation.LibraryGraph
import navid.multiplash.shared.navigation.SearchGraph

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        NavHost(
            navController = navController,
            startDestination = ExploreGraph,
            enterTransition = { fadeIn(tween(250)) },
            exitTransition = { fadeOut(tween(250)) },
            modifier = Modifier.fillMaxSize(),
        ) {
            exploreGraph(navController)
            searchGraph()
            libraryGraph()
        }
    }
}

private fun NavGraphBuilder.exploreGraph(
    navController: NavHostController,
) {
    navigation<ExploreGraph>(startDestination = ExploreScreen) {
        exploreScreen(
            onItemClick = { navController.navigate(DetailsScreen(url = it)) }
        )
        detailsScreen(
            onNavigationIconClick = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.searchGraph() {
    navigation<SearchGraph>(startDestination = SearchScreen) {
        searchScreen()
    }
}

private fun NavGraphBuilder.libraryGraph() {
    navigation<LibraryGraph>(startDestination = LibraryScreen) {
        libraryScreen()
    }
}
