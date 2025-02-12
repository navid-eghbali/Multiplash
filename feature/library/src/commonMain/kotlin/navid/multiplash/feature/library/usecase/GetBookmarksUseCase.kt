package navid.multiplash.feature.library.usecase

import androidx.paging.map
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import navid.multiplash.feature.library.data.repository.LibraryRepository

internal fun interface GetBookmarksUseCase {
    operator fun invoke(): Flow<PagingData<Bookmark>>

    data class Bookmark(
        val photoId: String,
        val photoUrl: String,
    )
}

internal class GetBookmarksUseCaseImpl(
    private val repository: LibraryRepository,
) : GetBookmarksUseCase {
    override fun invoke(): Flow<PagingData<GetBookmarksUseCase.Bookmark>> = repository.getBookmarks()
        .map { keyValues ->
            keyValues.map {
                GetBookmarksUseCase.Bookmark(
                    photoId = it.key,
                    photoUrl = it.value
                )
            }
        }
}
