package navid.multiplash.feature.photos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import navid.multiplash.feature.search.api.usecase.SearchPhotosUseCase

internal class PhotosViewModel(
    args: PhotosScreen,
    searchPhotosUseCase: SearchPhotosUseCase,
) : ViewModel() {

    val pagedPhotos = searchPhotosUseCase(query = args.query)
        .cachedIn(viewModelScope)
}
