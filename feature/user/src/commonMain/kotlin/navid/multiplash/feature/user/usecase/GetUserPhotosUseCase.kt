package navid.multiplash.feature.user.usecase

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import navid.multiplash.core.data.Photo
import navid.multiplash.feature.user.data.repository.UserRepository

internal fun interface GetUserPhotosUseCase {

    operator fun invoke(username: String): Flow<PagingData<Photo>>
}

internal class GetUserPhotosUseCaseImpl(
    private val userRepository: UserRepository,
) : GetUserPhotosUseCase {

    override fun invoke(username: String): Flow<PagingData<Photo>> = userRepository.getUserPhotos(username)
}
