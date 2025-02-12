package navid.multiplash.feature.user.usecase

import navid.multiplash.feature.user.data.repository.UserRepository

internal fun interface UnfavoriteUserUseCase {
    suspend operator fun invoke(username: String): Result<Unit>
}

internal class UnfavoriteUserUseCaseImpl(
    private val repository: UserRepository,
) : UnfavoriteUserUseCase {
    override suspend fun invoke(username: String): Result<Unit> = repository.unfavoriteUser(username)
}
