package navid.multiplash.feature.explore.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import navid.multiplash.feature.explore.data.model.Photo
import navid.multiplash.kodein.viewmodel.rememberViewModel

@Composable
internal fun ExploreUi(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: ExploreViewModel by rememberViewModel()
    val pagedPhotos = viewModel.pagedPhotos.collectAsLazyPagingItems()
    ExploreUi(
        pagedItems = pagedPhotos,
        onItemClick = onItemClick,
        modifier = modifier,
    )
}

@Composable
private fun ExploreUi(
    pagedItems: LazyPagingItems<Photo>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (val refreshLoadState = pagedItems.loadState.refresh) {
            is LoadStateLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is LoadStateError -> Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Error: ${refreshLoadState.error.message}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                )
                Button(onClick = { pagedItems.refresh() }) {
                    Text(text = "Reload")
                }
            }

            else -> Unit
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize(),
        ) {
            items(count = pagedItems.itemCount) { index ->
                pagedItems[index]?.let {
                    AsyncImage(
                        model = it.urls.regular,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable { onItemClick(it.urls.regular) },
                    )
                }
            }

            when (pagedItems.loadState.append) {
                is LoadStateLoading -> item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is LoadStateError -> item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Button(
                            onClick = { pagedItems.retry() },
                            modifier = Modifier.align(Alignment.Center),
                        ) {
                            Text(text = "Retry")
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}
