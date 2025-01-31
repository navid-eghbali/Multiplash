package navid.multiplash.feature.user.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import navid.multiplash.core.data.Photo
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.ic_downloads
import navid.multiplash.core.resources.ic_location
import navid.multiplash.core.resources.ic_views
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCase
import navid.multiplash.feature.user.usecase.GetUserUseCase
import navid.multiplash.kodein.viewmodel.rememberViewModel
import org.jetbrains.compose.resources.painterResource
import kotlin.math.min

@Composable
internal fun UserUi(
    args: UserScreen,
    onNavigationIconClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onInterestClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: UserViewModel by rememberViewModel(arg = args)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagedPhotos = viewModel.pagedPhotos.collectAsLazyPagingItems()

    UserUi(
        state = state,
        pagedItems = pagedPhotos,
        onNavigationIconClick = onNavigationIconClick,
        onReload = viewModel::onReload,
        onLocationClick = onLocationClick,
        onInterestClick = onInterestClick,
        onPhotoClick = onPhotoClick,
        onReloadStats = viewModel::onReloadStats,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserUi(
    state: UserState,
    pagedItems: LazyPagingItems<Photo>,
    onNavigationIconClick: () -> Unit,
    onReload: () -> Unit,
    onLocationClick: (String) -> Unit,
    onInterestClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
    onReloadStats: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val firstItemVisible by remember { derivedStateOf { gridState.firstVisibleItemIndex == 0 } }
    Scaffold(
        topBar = {
            if (!firstItemVisible) {
                TopAppBar(
                    title = { Text(text = state.user?.name.orEmpty()) },
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
                        text = "Error: ${state.errorMessage}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                    )
                    Button(onClick = onReload) { Text(text = "Reload") }
                }

                state.user != null -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        UserHeaderItem(
                            user = state.user,
                            stats = state.stats,
                            onReloadStats = onReloadStats,
                        )
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        UserInfoItem(
                            user = state.user,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                    state.user.bio?.let { bio ->
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            UserBioItem(
                                bio = bio,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                        }
                    }
                    state.user.location?.let { location ->
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            UserLocationItem(
                                location = location,
                                onLocationClick = onLocationClick,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                        }
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        UserFollowItem(
                            user = state.user,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                    if (state.user.interests.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            UserInterestsItem(
                                interests = state.user.interests,
                                onInterestClick = onInterestClick,
                            )
                        }
                    }
                    when (val refreshLoadState = pagedItems.loadState.refresh) {
                        is LoadStateLoading -> item(span = { GridItemSpan(maxLineSpan) }) {
                            CircularProgressIndicator(modifier = Modifier.size(48.dp))
                        }

                        is LoadStateError -> item(span = { GridItemSpan(maxLineSpan) }) {
                            ReloadItem(
                                errorMessage = refreshLoadState.error.message,
                                onReload = { pagedItems.refresh() },
                                modifier = Modifier.align(Alignment.Center),
                            )
                        }

                        else -> {
                            items(count = pagedItems.itemCount) { index ->
                                pagedItems[index]?.let {
                                    PhotoItem(photo = it, onPhotoClick = onPhotoClick)
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
private fun UserHeaderItem(
    user: GetUserUseCase.User,
    stats: GetUserStatisticsUseCase.Stats?,
    onReloadStats: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val localDensity = LocalDensity.current
    var headerHeight by remember { mutableStateOf(0.dp) }
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight + (PROFILE_IMAGE_HEIGHT / 2).dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.77F) // 16:9
                .onGloballyPositioned { coordinates -> headerHeight = with(localDensity) { coordinates.size.height.toDp() } }
                .align(Alignment.TopCenter),
        ) {
            val pagerState = rememberPagerState { user.photos.size }

            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                AsyncImage(
                    model = user.photos[page].urls.regular,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
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
        }
        AsyncImage(
            model = user.profileImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(PROFILE_IMAGE_HEIGHT.dp)
                .border(border = BorderStroke(width = 4.dp, color = MaterialTheme.colorScheme.background), shape = CircleShape)
                .clip(CircleShape)
                .align(Alignment.BottomStart),
        )
        StatisticsItem(
            stats = stats,
            onReloadStats = onReloadStats,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}

@Composable
private fun StatisticsItem(
    stats: GetUserStatisticsUseCase.Stats?,
    onReloadStats: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End,
        modifier = modifier.padding(end = 16.dp, bottom = 8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when {
                stats == null -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
                stats.totalDownloads.isEmpty() -> IconButton(onClick = onReloadStats) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Reload Statistics",
                    )
                }

                else -> Text(text = stats.totalDownloads, style = MaterialTheme.typography.titleMedium)
            }
            Icon(painter = painterResource(Res.drawable.ic_downloads), contentDescription = "Total Downloads")
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when {
                stats == null -> CircularProgressIndicator(modifier = Modifier.size(24.dp))
                stats.totalViews.isEmpty() -> IconButton(onClick = onReloadStats) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Reload Statistics",
                    )
                }

                else -> Text(text = stats.totalViews, style = MaterialTheme.typography.titleMedium)
            }
            Icon(painter = painterResource(Res.drawable.ic_views), contentDescription = "Total Views")
        }
    }
}

@Composable
private fun UserInfoItem(
    user: GetUserUseCase.User,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = user.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = user.username,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
private fun UserBioItem(
    bio: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = bio,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .padding(top = 8.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainerHighest, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(8.dp),
    )
}

@Composable
private fun UserLocationItem(
    location: String,
    onLocationClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_location),
            contentDescription = "Location",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = location,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.clickable { onLocationClick(location) },
        )
    }
}

@Composable
private fun UserFollowItem(
    user: GetUserUseCase.User,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = user.followingCount,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Following",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = user.followersCount,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Followers",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun UserInterestsItem(
    interests: List<String>,
    onInterestClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        items(interests) { item ->
            InputChip(
                selected = false,
                onClick = { onInterestClick(item) },
                label = { Text(text = item, style = MaterialTheme.typography.labelSmall) },
                shape = MaterialTheme.shapes.large,
                colors = InputChipDefaults.inputChipColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest),
                border = null,
            )
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
            .padding(16.dp),
    ) {
        Button(
            onClick = onRetry,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(text = "Retry")
        }
    }
}

private const val PROFILE_IMAGE_HEIGHT = 128
