package navid.multiplash.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.paging.cachedIn
import navid.multiplash.feature.home.usecase.GetPhotosUseCase

class HomeViewModel(
    getPhotosUseCase: GetPhotosUseCase,
) : ViewModel() {

    val pagedPhotos = getPhotosUseCase()
        .cachedIn(viewModelScope)
}
