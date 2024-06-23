package navid.multiplash.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import navid.multiplash.kodein.viewmodel.rememberViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel by rememberViewModel()
    val state by viewModel.state.collectAsState()
    HomeScreen(
        state = state,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is HomeUiState.Loading -> CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            is HomeUiState.Error -> Text(
                text = state.error,
                modifier = modifier.align(Alignment.Center),
            )

            is HomeUiState.Success -> {
                val items = state.photosPager.collectAsLazyPagingItems()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxSize(),
                ) {
                    if (items.loadState.refresh == LoadStateLoading) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                strokeCap = StrokeCap.Round,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }
                    items(count = items.itemCount) { index ->
                        items[index]?.let {
                            AsyncImage(
                                model = it.urls.regular,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.aspectRatio(1f)
                            )
                        }
                    }
                    if (items.loadState.append == LoadStateLoading) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    strokeCap = StrokeCap.Round,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
