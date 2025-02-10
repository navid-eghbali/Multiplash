package navid.multiplash.feature.explore.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import navid.multiplash.core.data.Photo
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.error
import navid.multiplash.core.resources.reload
import navid.multiplash.core.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
internal fun ExploreUi(
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: ExploreViewModel by rememberViewModel()
    val pagedPhotos = viewModel.pagedPhotos.collectAsLazyPagingItems()

    ExploreUi(
        pagedItems = pagedPhotos,
        onPhotoClick = onPhotoClick,
        modifier = modifier,
    )
}

@Composable
private fun ExploreUi(
    pagedItems: LazyPagingItems<Photo>,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (val refreshLoadState = pagedItems.loadState.refresh) {
            is LoadStateLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is LoadStateError -> ReloadItem(
                errorMessage = refreshLoadState.error.message,
                onReload = { pagedItems.refresh() },
                modifier = Modifier.align(Alignment.Center),
            )

            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize(),
            ) {
                items(count = pagedItems.itemCount) { index ->
                    pagedItems[index]?.let {
                        PhotoItem(
                            photo = it,
                            onPhotoClick = onPhotoClick,
                        )
                    }
                }

                when (pagedItems.loadState.append) {
                    is LoadStateLoading -> item(span = { GridItemSpan(maxLineSpan) }) { LoadingItem() }
                    is LoadStateError -> item(span = { GridItemSpan(maxLineSpan) }) { RetryItem(onRetry = { pagedItems.retry() }) }
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun PhotoItem(
    photo: Photo,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = photo.urls.regular,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onPhotoClick(photo.id, photo.urls.full) },
    )
}

@Composable
private fun LoadingItem(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun ReloadItem(
    errorMessage: String?,
    onReload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.error, errorMessage.orEmpty()),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
        )
        Button(onClick = onReload) { Text(text = stringResource(Res.string.reload)) }
    }
}

@Composable
private fun RetryItem(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Button(
            onClick = onRetry,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(text = stringResource(Res.string.retry))
        }
    }
}
