package navid.multiplash.feature.search.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

object SearchRouter {

    private const val ROUTE = "search"

    const val ROOT = "search-root"

    const val PATH = "$ROOT/$ROUTE"

    val arguments: List<NamedNavArgument> = listOf()

    val deepLinks: List<NavDeepLink> = listOf()
}
