package navid.multiplash.feature.library.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import navid.multiplash.feature.library.ui.LibraryScreen

fun NavGraphBuilder.libraryGraph() {
    composable(
        route = LibraryRouter.PATH,
        arguments = LibraryRouter.arguments,
        deepLinks = LibraryRouter.deepLinks,
    ) {
        LibraryScreen()
    }
}
