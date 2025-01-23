package navid.multiplash.feature.user.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class UserScreen(val username: String)

fun NavGraphBuilder.userScreen(
    onNavigationIconClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onInterestClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
) {
    composable<UserScreen> {
        UserUi(
            args = it.toRoute(),
            onNavigationIconClick = onNavigationIconClick,
            onLocationClick = onLocationClick,
            onInterestClick = onInterestClick,
            onPhotoClick = onPhotoClick,
        )
    }
}

fun NavController.navigateToUserScreen(username: String) {
    navigate(UserScreen(username = username))
}
