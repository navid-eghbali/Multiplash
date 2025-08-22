package navid.multiplash.feature.library.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.bookmarks
import navid.multiplash.core.resources.favorite_users
import navid.multiplash.core.resources.library
import navid.multiplash.core.resources.no_bookmarks
import navid.multiplash.core.resources.no_favorite_users
import navid.multiplash.feature.library.usecase.GetBookmarksUseCase
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
internal fun LibraryUi(
    onUserClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: LibraryViewModel by rememberViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pagedBookmarks = viewModel.pagedBookmarks.collectAsLazyPagingItems()

    LibraryUi(
        state = state,
        pagedItems = pagedBookmarks,
        onUserClick = onUserClick,
        onPhotoClick = onPhotoClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryUi(
    state: LibraryState,
    pagedItems: LazyPagingItems<GetBookmarksUseCase.Bookmark>,
    onUserClick: (String) -> Unit,
    onPhotoClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(Res.string.library)) }) },
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding),
        ) {
            when (state) {
                is LibraryState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is LibraryState.Content -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(Res.string.favorite_users),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        if (state.favoriteUsers.isNotEmpty()) {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                items(state.favoriteUsers) { user ->
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .width(96.dp)
                                            .wrapContentHeight()
                                            .clickable { onUserClick(user.username) }
                                            .padding(8.dp),
                                    ) {
                                        AsyncImage(
                                            model = user.profileImage,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .width(80.dp)
                                                .height(80.dp)
                                                .clip(CircleShape),
                                        )
                                        Text(
                                            text = user.username,
                                            textAlign = TextAlign.Center,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                            style = MaterialTheme.typography.labelMedium,
                                        )
                                    }
                                }
                            }
                        } else {
                            Text(
                                text = stringResource(Res.string.no_favorite_users),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(horizontal = 24.dp),
                            )
                        }
                    }
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(Res.string.bookmarks),
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                    if (pagedItems.loadState.refresh is LoadStateNotLoading) {
                        if (pagedItems.itemCount > 0) {
                            items(count = pagedItems.itemCount) { index ->
                                pagedItems[index]?.let {
                                    AsyncImage(
                                        model = it.photoUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .clickable { onPhotoClick(it.photoId, it.photoUrl) },
                                    )
                                }
                            }
                        } else {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    text = stringResource(Res.string.no_bookmarks),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(horizontal = 24.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
