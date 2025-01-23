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
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import navid.multiplash.core.data.Photo
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.ic_downloads
import navid.multiplash.core.resources.ic_location
import navid.multiplash.core.resources.ic_views
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
        onReload = viewModel::onReload,
        onNavigationIconClick = onNavigationIconClick,
        onLocationClick = onLocationClick,
        onInterestClick = onInterestClick,
        onPhotoClick = onPhotoClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserUi(
    state: UserState,
    pagedItems: LazyPagingItems<Photo>,
    onReload: () -> Unit,
    onNavigationIconClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onInterestClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    val firstItemVisible by remember { derivedStateOf { gridState.firstVisibleItemIndex == 0 } }
    Scaffold(
        topBar = {
            if (!firstItemVisible) {
                TopAppBar(
                    title = { Text(text = (state as? UserState.Success)?.name.orEmpty()) },
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
            when (state) {
                is UserState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is UserState.Error -> Column(
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

                is UserState.Success -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) { UserHeaderItem(state) }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        UserInfoItem(
                            state = state,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                    state.bio?.let { bio ->
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            UserBioItem(
                                bio = bio,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                        }
                    }
                    state.location?.let { location ->
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
                            state = state,
                            modifier = Modifier.padding(horizontal = 16.dp),
                        )
                    }
                    if (state.interests.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            UserInterestsItem(
                                interests = state.interests,
                                onInterestClick = onInterestClick,
                            )
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
private fun UserHeaderItem(
    state: UserState.Success,
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
            val pagerState = rememberPagerState { state.photos.size }

            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                AsyncImage(
                    model = state.photos[page].urls.regular,
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
            model = state.profileImage,
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
            state = state,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}

@Composable
private fun StatisticsItem(
    state: UserState.Success,
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
            Text(
                text = state.totalDownloads,
                style = MaterialTheme.typography.titleMedium,
            )
            Icon(painter = painterResource(Res.drawable.ic_downloads), contentDescription = "Total Downloads")
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = state.totalViews,
                style = MaterialTheme.typography.titleMedium,
            )
            Icon(painter = painterResource(Res.drawable.ic_views), contentDescription = "Total Views")
        }
    }
}

@Composable
private fun UserInfoItem(
    state: UserState.Success,
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
            text = state.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = state.username,
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
    state: UserState.Success,
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
                text = state.followingCount,
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
                text = state.followersCount,
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

private const val PROFILE_IMAGE_HEIGHT = 128
