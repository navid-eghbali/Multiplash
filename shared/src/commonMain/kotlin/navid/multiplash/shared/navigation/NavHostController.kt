package navid.multiplash.shared.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

internal fun NavHostController.navigateToRoute(route: Any) {
    navigate(route) {
        graph.findStartDestination().route?.let { startRoute ->
            popUpTo(startRoute) { saveState = true }
        }
        launchSingleTop = true
        restoreState = true
    }
}
