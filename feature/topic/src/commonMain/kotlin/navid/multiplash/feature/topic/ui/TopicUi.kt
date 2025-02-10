package navid.multiplash.feature.topic.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import navid.multiplash.core.data.Photo
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.error
import navid.multiplash.core.resources.reload
import navid.multiplash.core.resources.top_contributors
import navid.multiplash.core.resources.total_photos
import navid.multiplash.feature.topic.usecase.GetTopicUseCase
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.viewmodel.rememberViewModel
import kotlin.math.min

@Composable
internal fun TopicUi(
    args: TopicScreen,
    onNavigationIconClick: () -> Unit,
    onUserClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: TopicViewModel by rememberViewModel(arg = args)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagedPhotos = viewModel.pagedPhotos.collectAsLazyPagingItems()

    TopicUi(
        state = state,
        pagedItems = pagedPhotos,
        onNavigationIconClick = onNavigationIconClick,
        onReload = viewModel::onReload,
        onUserClick = onUserClick,
        onPhotoClick = onPhotoClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopicUi(
    state: TopicState,
    pagedItems: LazyPagingItems<Photo>,
    onNavigationIconClick: () -> Unit,
    onReload: () -> Unit,
    onUserClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val firstItemVisible by remember { derivedStateOf { gridState.firstVisibleItemIndex == 0 } }
    Scaffold(
        topBar = {
            if (!firstItemVisible) {
                TopAppBar(
                    title = { Text(text = state.topic?.title.orEmpty()) },
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
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                state.errorMessage != null -> Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.error, state.errorMessage),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                    )
                    Button(onClick = onReload) { Text(text = stringResource(Res.string.reload)) }
                }

                state.topic != null -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) { TopicHeaderItem(topic = state.topic) }
                    item(span = { GridItemSpan(maxLineSpan) }) { TopicDescriptionItem(topic = state.topic) }
                    if (state.topic.topContributors.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            TopicTopContributorsItem(topic = state.topic, onUserClick = onUserClick)
                        }
                    }
                    if (pagedItems.loadState.refresh is LoadStateNotLoading) {
                        items(count = pagedItems.itemCount) { index ->
                            pagedItems[index]?.let {
                                PhotoItem(photo = it, onPhotoClick = onPhotoClick)
                            }
                        }
                    }
                }
            }
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

@Composable
private fun TopicHeaderItem(
    topic: GetTopicUseCase.Topic,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.77F), // 16:9
    ) {
        val pagerState = rememberPagerState { topic.previewPhotos.size }

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            AsyncImage(
                model = topic.previewPhotos[page].urls.regular,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0F to Color.Transparent,
                            0.75F to MaterialTheme.colorScheme.background.copy(alpha = 0.5F),
                        ),
                    )
                ),
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25F)
                }
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
        ) {
            Text(
                text = topic.owners,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = topic.title,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                text = stringResource(Res.string.total_photos, topic.totalPhotos),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TopicDescriptionItem(
    topic: GetTopicUseCase.Topic,
    modifier: Modifier = Modifier,
) {
    Text(
        text = topic.description,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    )
}

@Composable
private fun TopicTopContributorsItem(
    topic: GetTopicUseCase.Topic,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        Text(
            text = stringResource(Res.string.top_contributors),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(topic.topContributors) { user ->
                AsyncImage(
                    model = user.profileImage.large,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .clip(CircleShape)
                        .clickable { onUserClick(user.username) },
                )
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
