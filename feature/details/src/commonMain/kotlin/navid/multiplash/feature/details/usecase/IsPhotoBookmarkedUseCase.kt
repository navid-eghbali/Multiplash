package navid.multiplash.feature.details.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import navid.multiplash.feature.details.data.repository.DetailsRepository

internal fun interface IsPhotoBookmarkedUseCase {
    operator fun invoke(photoId: String): Flow<Boolean>
}

internal class IsPhotoBookmarkedUseCaseImpl(
    private val repository: DetailsRepository,
) : IsPhotoBookmarkedUseCase {
    override fun invoke(photoId: String): Flow<Boolean> = repository.getBookmarks()
        .map { keyValues ->
            keyValues.any { it.key == photoId }
        }
}
