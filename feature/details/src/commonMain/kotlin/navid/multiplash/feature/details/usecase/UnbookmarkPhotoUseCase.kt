package navid.multiplash.feature.details.usecase

import navid.multiplash.feature.details.data.repository.DetailsRepository

internal fun interface UnbookmarkPhotoUseCase {
    suspend operator fun invoke(photoId: String): Result<Unit>
}

internal class UnbookmarkPhotoUseCaseImpl(
    private val repository: DetailsRepository,
) : UnbookmarkPhotoUseCase {
    override suspend fun invoke(photoId: String): Result<Unit> = repository.unbookmarkPhoto(photoId)
}
