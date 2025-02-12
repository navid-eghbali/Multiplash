package navid.multiplash.feature.user.usecase

import navid.multiplash.feature.user.data.repository.UserRepository

internal fun interface FavoriteUserUseCase {
    suspend operator fun invoke(
        username: String,
        profileImage: String,
    ): Result<Unit>
}

internal class FavoriteUserUseCaseImpl(
    private val repository: UserRepository,
) : FavoriteUserUseCase {
    override suspend fun invoke(username: String, profileImage: String): Result<Unit> =
        repository.favoriteUser(username, profileImage)
}
