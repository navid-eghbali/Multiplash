package navid.multiplash.feature.search.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.search.api.usecase.SearchPhotosUseCase
import navid.multiplash.feature.search.data.repository.SearchRepository

internal class SearchPhotosUseCaseImpl(
    private val searchRepository: SearchRepository,
) : SearchPhotosUseCase {

    override fun invoke(query: String): Flow<PagingData<Photo>> = searchRepository.searchPhotos(query)
}
