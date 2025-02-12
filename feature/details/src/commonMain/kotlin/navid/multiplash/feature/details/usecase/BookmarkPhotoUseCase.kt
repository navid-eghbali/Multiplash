package navid.multiplash.feature.details.usecase

import navid.multiplash.feature.details.data.repository.DetailsRepository

internal fun interface BookmarkPhotoUseCase {
    suspend operator fun invoke(
        photoId: String,
        photoUrl: String,
    ): Result<Unit>
}

internal class BookmarkPhotoUseCaseImpl(
    private val repository: DetailsRepository,
) : BookmarkPhotoUseCase {
    override suspend fun invoke(photoId: String, photoUrl: String): Result<Unit> =
        repository.bookmarkPhoto(photoId, photoUrl)
}
