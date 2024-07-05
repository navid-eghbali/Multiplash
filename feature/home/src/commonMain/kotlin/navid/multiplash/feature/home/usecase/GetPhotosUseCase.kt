package navid.multiplash.feature.home.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.feature.home.data.model.Photo
import navid.multiplash.feature.home.data.repository.HomeRepository

fun interface GetPhotosUseCase {

    operator fun invoke(): Flow<PagingData<Photo>>
}

internal class GetPhotosUseCaseImpl(
    private val homeRepository: HomeRepository,
) : GetPhotosUseCase {

    override fun invoke(): Flow<PagingData<Photo>> = homeRepository.getPhotos()
}
