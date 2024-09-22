package navid.multiplash.feature.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import navid.multiplash.feature.search.ui.SearchScreen
import navid.multiplash.feature.search.ui.SearchUi

fun NavGraphBuilder.searchScreen(
    onItemClick: (String) -> Unit,
) {
    composable<SearchScreen> {
        SearchUi(
            onItemClick = onItemClick,
        )
    }
}
