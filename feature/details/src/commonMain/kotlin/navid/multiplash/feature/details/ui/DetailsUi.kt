package navid.multiplash.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import navid.multiplash.core.resources.LocalPlatformStrings
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.details
import navid.multiplash.core.resources.downloads
import navid.multiplash.core.resources.ic_camera
import navid.multiplash.core.resources.ic_date
import navid.multiplash.core.resources.ic_location
import navid.multiplash.core.resources.likes
import navid.multiplash.core.resources.published_date
import navid.multiplash.core.resources.total_photos
import navid.multiplash.core.resources.views
import navid.multiplash.feature.details.ui.DetailsViewModel.Event
import navid.multiplash.feature.details.usecase.GetPhotoUseCase
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.viewmodel.rememberViewModel

@Composable
internal fun DetailsUi(
    args: DetailsScreen,
    onNavigationIconClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onTagClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: DetailsViewModel by rememberViewModel(arg = args)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is Event.Notification -> scope.launch { snackbarHostState.showSnackbar(message = event.message) }
            }
        }
    }

    DetailsUi(
        state = state,
        snackbarHostState = snackbarHostState,
        onNavigationIconClick = onNavigationIconClick,
        onLocationClick = onLocationClick,
        onTagClick = onTagClick,
        onUserClick = onUserClick,
        onSaveClick = viewModel::onSaveClick,
        onImageLoading = viewModel::onImageLoading,
        onImageComplete = viewModel::onImageComplete,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsUi(
    state: DetailsState,
    snackbarHostState: SnackbarHostState,
    onNavigationIconClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onTagClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onSaveClick: (String, String) -> Unit,
    onImageLoading: () -> Unit,
    onImageComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1F) }
    var offset by remember { mutableStateOf(Offset(0F, 0F)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onNavigationIconClick,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.33F)),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showBottomSheet = true },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.33F)),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Color.Transparent)
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize(),
    ) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        BoxWithConstraints {
            AsyncImage(
                model = state.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = { onImageLoading() },
                onSuccess = { onImageComplete() },
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale *= zoom
                            scale = scale.coerceIn(0.5F, 2F)
                            offset = if (scale == 1F) Offset(0F, 0F) else offset + pan
                        }
                    }
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y,
                    ),
            )
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    ) {
                        HeaderItem(
                            onHideClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) showBottomSheet = false
                                }
                            },
                        )
                        state.photo?.let { photo ->
                            StatisticsItem(photo = photo)
                            photo.description?.let { DescriptionItem(description = it) }
                            photo.location?.let { LocationItem(location = it, onLocationClick = onLocationClick) }
                            DateItem(
                                date = stringResource(
                                    Res.string.published_date,
                                    photo.publishedDateMonth,
                                    photo.publishedDateDay,
                                    photo.publishedDateYear,
                                )
                            )
                            photo.device?.let { DeviceItem(device = it) }
                            TagsItem(tags = photo.tags, onTagClick = onTagClick)
                            ProfileItem(photo = photo, onUserClick = onUserClick)
                            Button(
                                onClick = { onSaveClick(photo.id, photo.downloadLink) },
                                enabled = !state.isDownloading,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                            ) {
                                if (state.isDownloading) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                } else {
                                    Text(text = stringResource(LocalPlatformStrings.current.savePhoto))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderItem(
    onHideClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.details),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Center),
        )
        IconButton(onClick = onHideClick) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null,
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter))
    }
}

@Composable
private fun StatisticsItem(
    photo: GetPhotoUseCase.Photo,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        if (photo.views != "0") {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                Text(text = photo.views, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = stringResource(Res.string.views),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        if (photo.likes != "0") {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                Text(text = photo.likes, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = stringResource(Res.string.likes),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        if (photo.downloads != "0") {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                Text(text = photo.downloads, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = stringResource(Res.string.downloads),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Composable
private fun DescriptionItem(
    description: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainerHighest, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .padding(8.dp),
    )
}

@Composable
private fun LocationItem(
    location: String,
    onLocationClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onLocationClick(location) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Icon(painter = painterResource(Res.drawable.ic_location), contentDescription = "Location")
        Text(text = location, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun DateItem(
    date: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Icon(painter = painterResource(Res.drawable.ic_date), contentDescription = "Published Date")
        Text(text = date, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun DeviceItem(
    device: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
    ) {
        Icon(painter = painterResource(Res.drawable.ic_camera), contentDescription = "EXIF")
        Text(text = device, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun TagsItem(
    tags: List<String>,
    onTagClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        items(tags) { tag ->
            InputChip(
                selected = false,
                onClick = { onTagClick(tag) },
                label = { Text(text = tag, style = MaterialTheme.typography.labelSmall) },
                shape = MaterialTheme.shapes.large,
                colors = InputChipDefaults.inputChipColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest),
                border = null,
            )
        }
    }
}

@Composable
private fun ProfileItem(
    photo: GetPhotoUseCase.Photo,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = photo.user.profileImage.large,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clip(CircleShape)
                .clickable { onUserClick(photo.user.username) },
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.clickable { onUserClick(photo.user.username) },
        ) {
            Text(text = photo.user.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = stringResource(Res.string.total_photos, photo.userTotalPhotos),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
