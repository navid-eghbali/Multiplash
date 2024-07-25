package navid.multiplash.feature.explore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import navid.multiplash.feature.explore.usecase.GetPhotosUseCase

class ExploreViewModel(
    getPhotosUseCase: GetPhotosUseCase,
) : ViewModel() {

    val pagedPhotos = getPhotosUseCase()
        .cachedIn(viewModelScope)
}
