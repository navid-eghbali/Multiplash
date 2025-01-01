package navid.multiplash.feature.search.api.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo

fun interface SearchPhotosUseCase {
    operator fun invoke(query: String): Flow<PagingData<Photo>>
}
