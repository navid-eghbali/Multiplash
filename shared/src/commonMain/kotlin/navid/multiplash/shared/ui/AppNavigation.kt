package navid.multiplash.shared.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import navid.multiplash.feature.explore.navigation.ExploreRouter
import navid.multiplash.feature.explore.navigation.exploreGraph
import navid.multiplash.feature.library.navigation.LibraryRouter
import navid.multiplash.feature.library.navigation.libraryGraph
import navid.multiplash.feature.search.navigation.SearchRouter
import navid.multiplash.feature.search.navigation.searchGraph

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ExploreRouter.ROOT,
        enterTransition = { fadeIn(tween(250)) },
        exitTransition = { fadeOut(tween(250)) },
        modifier = modifier.fillMaxSize(),
    ) {
        exploreRootGraph()
        searchRootGraph()
        libraryRootGraph()
    }
}

private fun NavGraphBuilder.exploreRootGraph() {
    navigation(
        startDestination = ExploreRouter.PATH,
        route = ExploreRouter.ROOT,
    ) {
        exploreGraph()
    }
}

private fun NavGraphBuilder.searchRootGraph() {
    navigation(
        startDestination = SearchRouter.PATH,
        route = SearchRouter.ROOT,
    ) {
        searchGraph()
    }
}

private fun NavGraphBuilder.libraryRootGraph() {
    navigation(
        startDestination = LibraryRouter.PATH,
        route = LibraryRouter.ROOT,
    ) {
        libraryGraph()
    }
}
