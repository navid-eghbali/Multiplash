package navid.multiplash.feature.explore.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

object ExploreRouter {

    private const val ROUTE = "explore"

    const val ROOT = "explore-root"

    const val PATH = "$ROOT/$ROUTE"

    val arguments: List<NamedNavArgument> = listOf()

    val deepLinks: List<NavDeepLink> = listOf()
}
