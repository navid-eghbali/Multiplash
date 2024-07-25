package navid.multiplash.shared.navigation

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class NavigationType {
    NAVIGATION_BAR,
    NAVIGATION_RAIL,
    NAVIGATION_DRAWER;

    companion object {
        fun forWindowSizeClass(windowSizeClass: WindowSizeClass) = when {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact -> NAVIGATION_BAR
            windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact -> NAVIGATION_BAR
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> NAVIGATION_RAIL
            else -> NAVIGATION_DRAWER
        }
    }
}
