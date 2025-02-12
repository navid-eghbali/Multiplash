package navid.multiplash.feature.library.usecase

import navid.multiplash.feature.library.data.repository.LibraryRepository

internal fun interface GetFavoriteUsersUseCase {
    suspend operator fun invoke(): Result<List<FavoriteUser>>

    data class FavoriteUser(
        val username: String,
        val profileImage: String,
    )
}

internal class GetFavoriteUsersUseCaseImpl(
    private val repository: LibraryRepository,
) : GetFavoriteUsersUseCase {
    override suspend fun invoke(): Result<List<GetFavoriteUsersUseCase.FavoriteUser>> = repository.getFavorites()
        .fold(
            onSuccess = { keyValues ->
                Result.success(
                    keyValues.map {
                        GetFavoriteUsersUseCase.FavoriteUser(
                            username = it.key,
                            profileImage = it.value
                        )
                    }
                )
            },
            onFailure = { Result.failure(it) },
        )
}
