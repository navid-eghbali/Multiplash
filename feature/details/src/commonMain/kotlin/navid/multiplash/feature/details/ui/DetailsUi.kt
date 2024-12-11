package navid.multiplash.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import navid.multiplash.core.resources.Res
import navid.multiplash.core.resources.ic_camera
import navid.multiplash.core.resources.ic_date
import navid.multiplash.core.resources.ic_location
import navid.multiplash.feature.details.usecase.GetPhotoUseCase
import navid.multiplash.kodein.viewmodel.rememberViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun DetailsUi(
    args: DetailsScreen,
    onNavigationIconClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onTagClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: DetailsViewModel by rememberViewModel(arg = args)
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailsUi(
        state = state,
        onNavigationIconClick = onNavigationIconClick,
        onLocationClick = onLocationClick,
        onTagClick = onTagClick,
        onImageLoading = viewModel::onImageLoading,
        onImageComplete = viewModel::onImageComplete,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailsUi(
    state: DetailsState,
    onNavigationIconClick: () -> Unit,
    onLocationClick: (String) -> Unit,
    onTagClick: (String) -> Unit,
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
                            DateItem(date = photo.publishedDate)
                            photo.device?.let { DeviceItem(device = it) }
                            TagsItem(tags = photo.tags, onTagClick = onTagClick)
                            ProfileItem(photo = photo)
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
            text = "Details",
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
                Text(text = "Views", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
            }
        }
        if (photo.likes != "0") {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                Text(text = photo.likes, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Text(text = "Likes", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
            }
        }
        if (photo.downloads != "0") {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                Text(text = photo.downloads, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Text(text = "Downloads", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
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
                .clickable { },
        )
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = photo.user.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = photo.userTotalPhotos,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
