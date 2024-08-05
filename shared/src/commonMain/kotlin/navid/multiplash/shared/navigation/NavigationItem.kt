package navid.multiplash.shared.navigation

import androidx.compose.runtime.Immutable
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.explore
import navid.multiplash.core.resources.ic_explore_selected
import navid.multiplash.core.resources.ic_explore_unselected
import navid.multiplash.core.resources.ic_library_selected
import navid.multiplash.core.resources.ic_library_unselected
import navid.multiplash.core.resources.ic_search_selected
import navid.multiplash.core.resources.ic_search_unselected
import navid.multiplash.core.resources.library
import navid.multiplash.core.resources.search
import navid.multiplash.feature.explore.navigation.ExploreRouter
import navid.multiplash.feature.library.navigation.LibraryRouter
import navid.multiplash.feature.search.navigation.SearchRouter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Immutable
data class NavigationItem(
    val route: String,
    val labelRes: StringResource,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
)

fun getNavigationItems() = listOf(
    NavigationItem(
        route = ExploreRouter.PATH,
        labelRes = Res.string.explore,
        selectedIcon = Res.drawable.ic_explore_selected,
        unselectedIcon = Res.drawable.ic_explore_unselected,
    ),
    NavigationItem(
        route = SearchRouter.PATH,
        labelRes = Res.string.search,
        selectedIcon = Res.drawable.ic_search_selected,
        unselectedIcon = Res.drawable.ic_search_unselected,
    ),
    NavigationItem(
        route = LibraryRouter.PATH,
        labelRes = Res.string.library,
        selectedIcon = Res.drawable.ic_library_selected,
        unselectedIcon = Res.drawable.ic_library_unselected,
    ),
)
