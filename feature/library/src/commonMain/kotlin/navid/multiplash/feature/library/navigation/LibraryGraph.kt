package navid.multiplash.feature.library.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import navid.multiplash.feature.library.ui.LibraryScreen
import navid.multiplash.feature.library.ui.LibraryUi

fun NavGraphBuilder.libraryScreen() {
    composable<LibraryScreen> {
        LibraryUi()
    }
}
