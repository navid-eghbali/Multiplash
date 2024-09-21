package navid.multiplash.feature.explore.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.feature.explore.data.model.Photo
import navid.multiplash.feature.explore.data.repository.ExploreRepository

internal fun interface GetPhotosUseCase {

    operator fun invoke(): Flow<PagingData<Photo>>
}

internal class GetPhotosUseCaseImpl(
    private val exploreRepository: ExploreRepository,
) : GetPhotosUseCase {

    override fun invoke(): Flow<PagingData<Photo>> = exploreRepository.getPhotos()
}
