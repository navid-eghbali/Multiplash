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
import navid.multiplash.feature.explore.ui.ExploreScreen
import navid.multiplash.feature.library.ui.LibraryScreen
import navid.multiplash.feature.search.ui.SearchScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Immutable
data class NavigationItem(
    val route: Any,
    val labelRes: StringResource,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
)

fun getNavigationItems() = listOf(
    NavigationItem(
        route = ExploreScreen,
        labelRes = Res.string.explore,
        selectedIcon = Res.drawable.ic_explore_selected,
        unselectedIcon = Res.drawable.ic_explore_unselected,
    ),
    NavigationItem(
        route = SearchScreen,
        labelRes = Res.string.search,
        selectedIcon = Res.drawable.ic_search_selected,
        unselectedIcon = Res.drawable.ic_search_unselected,
    ),
    NavigationItem(
        route = LibraryScreen,
        labelRes = Res.string.library,
        selectedIcon = Res.drawable.ic_library_selected,
        unselectedIcon = Res.drawable.ic_library_unselected,
    ),
)
