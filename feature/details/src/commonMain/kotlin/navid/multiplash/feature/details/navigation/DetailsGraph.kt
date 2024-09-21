package navid.multiplash.feature.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import navid.multiplash.feature.details.ui.DetailsScreen
import navid.multiplash.feature.details.ui.DetailsUi

fun NavGraphBuilder.detailsScreen(
    onNavigationIconClick: () -> Unit,
) {
    composable<DetailsScreen> {
        DetailsUi(
            args = it.toRoute<DetailsScreen>(),
            onNavigationIconClick = onNavigationIconClick,
        )
    }
}
