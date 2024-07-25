package navid.multiplash.shared.ui

import navid.multiplash.shared.navigation.NavigationItem
import navid.multiplash.shared.navigation.getNavigationItems

data class AppState(
    val selectedRoute: String = "explore",
    val navigationDestinations: List<NavigationItem> = getNavigationItems(),
)
