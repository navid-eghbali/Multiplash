package navid.multiplash.feature.library.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object LibraryScreen

fun NavGraphBuilder.libraryScreen() {
    composable<LibraryScreen> {
        LibraryUi()
    }
}
