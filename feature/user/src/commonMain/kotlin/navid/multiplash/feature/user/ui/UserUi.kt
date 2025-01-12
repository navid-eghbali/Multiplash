package navid.multiplash.feature.user.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import navid.multiplash.kodein.viewmodel.rememberViewModel
import kotlin.math.min

@Composable
internal fun UserUi(
    args: UserScreen,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: UserViewModel by rememberViewModel(arg = args)
    val state by viewModel.state.collectAsStateWithLifecycle()

    UserUi(
        state = state,
        onNavigationIconClick = onNavigationIconClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserUi(
    state: UserState,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val firstItemVisible by remember { derivedStateOf { gridState.firstVisibleItemIndex == 0 } }
    Scaffold(
        topBar = {
            if (!firstItemVisible) {
                TopAppBar(
                    title = { Text(text = "") },
                    navigationIcon = {
                        IconButton(onClick = onNavigationIconClick) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                        }
                    },
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = onNavigationIconClick,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .alpha(1F - min(1, gridState.firstVisibleItemIndex))
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.33F))
                    .align(Alignment.TopStart),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}
