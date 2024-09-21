package navid.multiplash.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import navid.multiplash.kodein.viewmodel.rememberViewModel
import org.kodein.di.compose.subDI

@Composable
internal fun DetailsUi(
    args: DetailsScreen,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: DetailsViewModel by rememberViewModel(arg = args)
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailsUi(
        state = state,
        onNavigationIconClick = onNavigationIconClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsUi(
    state: DetailsState,
    onNavigationIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClick) { Icon(Icons.AutoMirrored.Rounded.ArrowBack, null) }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxSize()
                .padding(padding),
        ) {
            Text(
                text = state.url,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
