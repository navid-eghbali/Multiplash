package navid.multiplash.shared.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import navid.multiplash.core.ui.AppTheme
import navid.multiplash.core.ui.LocalWindowSizeClass
import navid.multiplash.feature.explore.ui.ExploreScreen
import navid.multiplash.feature.library.ui.LibraryScreen
import navid.multiplash.feature.search.ui.SearchScreen
import navid.multiplash.kodein.viewmodel.rememberViewModel
import navid.multiplash.shared.di.appModule
import navid.multiplash.shared.navigation.NavigationItem
import navid.multiplash.shared.navigation.NavigationType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.withDI

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun App(
    dynamicColor: Boolean,
    modifier: Modifier = Modifier,
) = withDI(appModule) {
    CompositionLocalProvider(
        LocalWindowSizeClass provides calculateWindowSizeClass(),
    ) {
        AppTheme(
            darkTheme = isSystemInDarkTheme(),
            dynamicColor = dynamicColor,
        ) {
            val viewModel: AppViewModel by rememberViewModel()
            val state: AppState by viewModel.state.collectAsState()
            MainContent(
                state = state,
                onNavigationItemClick = viewModel::onNavigationItemClicked,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun MainContent(
    state: AppState,
    onNavigationItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val windowSizeClass = LocalWindowSizeClass.current
    val navigationType = remember(windowSizeClass) {
        NavigationType.forWindowSizeClass(windowSizeClass)
    }
    Scaffold(
        bottomBar = {
            if (navigationType == NavigationType.NAVIGATION_BAR) {
                AppNavigationBar(
                    selectedRoute = state.selectedRoute,
                    navigationDestinations = state.navigationDestinations,
                    onNavigationItemClick = onNavigationItemClick,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (navigationType == NavigationType.NAVIGATION_RAIL) {
                AppNavigationRail(
                    selectedRoute = state.selectedRoute,
                    navigationDestinations = state.navigationDestinations,
                    onNavigationItemClick = onNavigationItemClick,
                )
            } else if (navigationType == NavigationType.NAVIGATION_DRAWER) {
                AppNavigationDrawer(
                    selectedRoute = state.selectedRoute,
                    navigationDestinations = state.navigationDestinations,
                    onNavigationItemClick = onNavigationItemClick,
                )
            }
            NavHost(
                navController = navController,
                startDestination = "search",
                enterTransition = { fadeIn(tween(250)) },
                exitTransition = { fadeOut(tween(250)) },
                modifier = Modifier.fillMaxSize(),
            ) {
                composable("explore") {
                    ExploreScreen()
                }
                composable("search") {
                    SearchScreen()
                }
                composable("library") {
                    LibraryScreen()
                }
            }
        }
    }

}

@Composable
private fun AppNavigationBar(
    selectedRoute: String,
    navigationDestinations: List<NavigationItem>,
    onNavigationItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        NavigationBar {
            navigationDestinations.forEach { item ->
                val selected = item.route == selectedRoute
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigationItemClick(item.route) },
                    icon = { Icon(painterResource(if (selected) item.selectedIcon else item.unselectedIcon), null) },
                    label = { Text(text = stringResource(item.labelRes)) },
                )
            }
        }
    }
}

@Composable
private fun AppNavigationRail(
    selectedRoute: String,
    navigationDestinations: List<NavigationItem>,
    onNavigationItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        navigationDestinations.forEach { item ->
            val selected = item.route == selectedRoute
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigationItemClick(item.route) },
                icon = { Icon(painterResource(if (selected) item.selectedIcon else item.unselectedIcon), null) },
                label = { Text(text = stringResource(item.labelRes)) },
            )
        }
    }
}

@Composable
private fun AppNavigationDrawer(
    selectedRoute: String,
    navigationDestinations: List<NavigationItem>,
    onNavigationItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeContent)
            .padding(16.dp)
            .widthIn(max = 280.dp)
            .fillMaxHeight(),
    ) {
        navigationDestinations.forEach { item ->
            val selected = item.route == selectedRoute
            NavigationDrawerItem(
                label = { Text(text = stringResource(item.labelRes)) },
                selected = selected,
                onClick = { onNavigationItemClick(item.route) },
                icon = { Icon(painterResource(if (selected) item.selectedIcon else item.unselectedIcon), null) },
            )
        }
    }
}
