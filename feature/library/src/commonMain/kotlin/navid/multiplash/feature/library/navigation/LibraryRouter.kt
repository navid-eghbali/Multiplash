package navid.multiplash.feature.library.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

object LibraryRouter {

    private const val ROUTE = "library"

    const val ROOT = "library-root"

    const val PATH = "$ROOT/$ROUTE"

    val arguments: List<NamedNavArgument> = listOf()

    val deepLinks: List<NavDeepLink> = listOf()
}
