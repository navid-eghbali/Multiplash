package navid.multiplash.shared.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import navid.multiplash.core.resources.LocalPlatformStrings
import navid.multiplash.core.resources.platformStrings
import navid.multiplash.core.ui.AppTheme
import navid.multiplash.core.ui.LocalWindowSizeClass
import navid.multiplash.shared.di.appModule
import navid.multiplash.shared.navigation.NavigationItem
import navid.multiplash.shared.navigation.NavigationType
import navid.multiplash.shared.navigation.navigateToRoute
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.viewmodel.rememberViewModel
import org.kodein.di.compose.withDI

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun App(
    dynamicColor: Boolean,
    modifier: Modifier = Modifier,
) = withDI(appModule) {
    CompositionLocalProvider(
        LocalWindowSizeClass provides calculateWindowSizeClass(),
        LocalPlatformStrings provides platformStrings(),
    ) {
        AppTheme(
            darkTheme = isSystemInDarkTheme(),
            dynamicColor = dynamicColor,
        ) {
            val viewModel: AppViewModel by rememberViewModel()
            val state: AppState by viewModel.state.collectAsStateWithLifecycle()

            AppUi(
                state = state,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun AppUi(
    state: AppState,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val windowSizeClass = LocalWindowSizeClass.current
    val navigationType = remember(windowSizeClass) {
        NavigationType.forWindowSizeClass(windowSizeClass)
    }
    val entry by navController.currentBackStackEntryAsState()
    val currentDestination = entry?.destination
    val showBottomBar = state.navigationDestinations.any { currentDestination?.hasRoute(it.route::class) == true }
    Scaffold(
        bottomBar = {
            if (navigationType == NavigationType.NAVIGATION_BAR && showBottomBar) {
                AppNavigationBar(
                    navBackStackEntry = entry,
                    navigationDestinations = state.navigationDestinations,
                    onNavigationItemClick = { navController.navigateToRoute(it) },
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding),
        ) {
            if (navigationType == NavigationType.NAVIGATION_RAIL) {
                AppNavigationRail(
                    navBackStackEntry = entry,
                    navigationDestinations = state.navigationDestinations,
                    onNavigationItemClick = { navController.navigateToRoute(it) },
                )
            } else if (navigationType == NavigationType.NAVIGATION_DRAWER) {
                AppNavigationDrawer(
                    navBackStackEntry = entry,
                    navigationDestinations = state.navigationDestinations,
                    onNavigationItemClick = { navController.navigateToRoute(it) },
                )
            }
            AppNavigation(navController)
        }
    }
}

@Composable
private fun AppNavigationBar(
    navBackStackEntry: NavBackStackEntry?,
    navigationDestinations: List<NavigationItem>,
    onNavigationItemClick: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        NavigationBar {
            val currentDestination = navBackStackEntry?.destination
            navigationDestinations.forEach { item ->
                val selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigationItemClick(item.route) },
                    icon = {
                        Icon(
                            painter = painterResource(if (selected) item.selectedIcon else item.unselectedIcon),
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(item.labelRes)) },
                )
            }
        }
    }
}

@Composable
private fun AppNavigationRail(
    navBackStackEntry: NavBackStackEntry?,
    navigationDestinations: List<NavigationItem>,
    onNavigationItemClick: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        val currentDestination = navBackStackEntry?.destination
        navigationDestinations.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigationItemClick(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(if (selected) item.selectedIcon else item.unselectedIcon),
                        contentDescription = null
                    )
                },
                label = { Text(text = stringResource(item.labelRes)) },
            )
        }
    }
}

@Composable
private fun AppNavigationDrawer(
    navBackStackEntry: NavBackStackEntry?,
    navigationDestinations: List<NavigationItem>,
    onNavigationItemClick: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeContent)
            .padding(16.dp)
            .widthIn(max = 280.dp)
            .fillMaxHeight(),
    ) {
        val currentDestination = navBackStackEntry?.destination
        navigationDestinations.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            NavigationDrawerItem(
                label = { Text(text = stringResource(item.labelRes)) },
                selected = selected,
                onClick = { onNavigationItemClick(item.route) },
                icon = {
                    Icon(
                        painter = painterResource(if (selected) item.selectedIcon else item.unselectedIcon),
                        contentDescription = null
                    )
                },
            )
        }
    }
}
