package navid.multiplash.feature.photos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import navid.multiplash.feature.photos.usecase.GetPhotosUseCase

internal class PhotosViewModel(
    args: PhotosScreen,
    getPhotosUseCase: GetPhotosUseCase,
) : ViewModel() {

    val pagedPhotos = getPhotosUseCase(query = args.query)
        .cachedIn(viewModelScope)
}
