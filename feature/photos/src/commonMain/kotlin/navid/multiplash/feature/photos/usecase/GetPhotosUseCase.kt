package navid.multiplash.feature.photos.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.photos.data.repository.PhotosRepository

internal fun interface GetPhotosUseCase {
    operator fun invoke(query: String): Flow<PagingData<Photo>>
}

internal class GetPhotosUseCaseImpl(
    private val photosRepository: PhotosRepository,
) : GetPhotosUseCase {

    override fun invoke(query: String): Flow<PagingData<Photo>> = photosRepository.photos(query)
}
