package navid.multiplash.feature.user.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import navid.multiplash.feature.user.data.repository.UserRepository

internal fun interface IsUserFavoritedUseCase {
    operator fun invoke(username: String): Flow<Boolean>
}

internal class IsUserFavoritedUseCaseImpl(
    private val repository: UserRepository,
) : IsUserFavoritedUseCase {
    override fun invoke(username: String): Flow<Boolean> = repository.getFavorites()
        .map { keyValues ->
            keyValues.any { it.key == username }
        }
}
