package navid.multiplash.feature.home.usecase

import navid.multiplash.feature.home.data.model.Photo
import navid.multiplash.feature.home.data.repository.HomeRepository

fun interface GetPhotosUseCase {

    suspend operator fun invoke(): Result<List<Photo>>
}

internal class GetPhotosUseCaseImpl(
    private val homeRepository: HomeRepository,
) : GetPhotosUseCase {

    override suspend fun invoke(): Result<List<Photo>> = homeRepository.getPhotos()
        .fold(
            onSuccess = { Result.success(it.photos) },
            onFailure = { Result.failure(it) },
        )
}
