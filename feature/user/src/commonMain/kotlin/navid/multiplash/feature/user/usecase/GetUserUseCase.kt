package navid.multiplash.feature.user.usecase

import navid.multiplash.core.data.Photo
import navid.multiplash.feature.user.data.remote.UserClient

internal fun interface GetUserUseCase {

    suspend operator fun invoke(username: String): Result<User>

    data class User(
        val name: String,
        val username: String,
        val photos: List<Photo>,
        val profileImage: String,
        val bio: String?,
        val location: String?,
        val followingCount: String,
        val followersCount: String,
        val interests: List<String>,
    )

}

internal class GetUserUseCaseImpl(
    private val userClient: UserClient,
) : GetUserUseCase {

    override suspend fun invoke(username: String): Result<GetUserUseCase.User> = userClient.getUser(username).fold(
        onSuccess = { response ->
            with(response) {
                Result.success(
                    GetUserUseCase.User(
                        name = name,
                        username = username,
                        photos = photos,
                        profileImage = profileImage.large,
                        bio = bio,
                        location = location,
                        followingCount = followingCount.toString(),
                        followersCount = followersCount.toString(),
                        interests = tags.interests.map { tag ->
                            tag.title.lowercase().replaceFirstChar { it.titlecase() }
                        },
                    )
                )
            }
        },
        onFailure = { Result.failure(it) }
    )

}
