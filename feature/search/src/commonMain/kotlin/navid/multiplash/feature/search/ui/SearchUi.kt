package navid.multiplash.feature.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.ic_chevron_right
import navid.multiplash.feature.search.data.model.Photo
import navid.multiplash.feature.search.usecase.GetTopicsUseCase
import navid.multiplash.kodein.viewmodel.rememberViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SearchUi(
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: SearchViewModel by rememberViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagedPhotos = viewModel.pagedPhotos.collectAsLazyPagingItems()

    SearchUi(
        state = state,
        pagedItems = pagedPhotos,
        onQueryChange = viewModel::onQueryChange,
        onItemClick = onItemClick,
        modifier = modifier,
    )
}

@Composable
private fun SearchUi(
    state: SearchState,
    pagedItems: LazyPagingItems<Photo>,
    onQueryChange: (String) -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
    ) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { onQueryChange(it) },
            placeholder = { Text(text = "What do you want to see?") },
            leadingIcon = { Icon(Icons.Rounded.Search, null) },
            trailingIcon = {
                if (state.query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Rounded.Clear, null)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(percent = 50),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        if (state.isLoading) {
            LoadingItem()
        }
        if (state.query.isEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(state.topics) { TopicItem(topic = it, onTopicClick = {}) }
            }
        } else {
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
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            TopicSuggestionsItem(
                                topics = state.topics,
                                onTopicClick = {},
                            )
                        }
                        items(count = pagedItems.itemCount) { index ->
                            pagedItems[index]?.let {
                                PhotoItem(
                                    photo = it,
                                    onItemClick = onItemClick,
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
    }
}

@Composable
private fun TopicItem(
    topic: GetTopicsUseCase.Topic,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        shadowElevation = 1.dp,
        modifier = modifier
            .aspectRatio(1F)
            .clickable { onTopicClick(topic.id) },
    ) {
        Box {
            AsyncImage(
                model = topic.coverUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .alpha(0.25F)
                    .aspectRatio(1F)
                    .fillMaxSize(),
            )
            Text(
                text = topic.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            ) {
                Text(
                    text = topic.totalPhotos,
                    style = MaterialTheme.typography.labelMedium,
                )
                Icon(painter = painterResource(Res.drawable.ic_chevron_right), null)
            }
        }
    }
}

@Composable
private fun TopicSuggestionsItem(
    topics: List<GetTopicsUseCase.Topic>,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        items(topics) { topic ->
            SuggestionChip(
                onClick = { onTopicClick(topic.id) },
                label = { Text(text = topic.title) },
                shape = RoundedCornerShape(50),
            )
        }
    }
}

@Composable
private fun PhotoItem(
    photo: Photo,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = photo.urls.small,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .aspectRatio(1F)
            .clickable { onItemClick(photo.urls.full) },
    )
}

@Composable
private fun LoadingItem(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
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
            text = "Error: $errorMessage",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
        )
        Button(onClick = onReload) { Text(text = "Reload") }
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
            .padding(16.dp)
    ) {
        Button(
            onClick = onRetry,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(text = "Retry")
        }
    }
}
