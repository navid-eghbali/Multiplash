package navid.multiplash.feature.details.usecase

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import navid.multiplash.common.ext.fromHexColorToLong
import navid.multiplash.common.ext.withDecimalSeparator
import navid.multiplash.core.data.User
import navid.multiplash.feature.details.data.remote.DetailsClient

internal fun interface GetPhotoUseCase {

    suspend operator fun invoke(photoId: String): Result<Photo>

    data class Photo(
        val id: String,
        val color: Long?,
        val views: String,
        val downloads: String,
        val likes: String,
        val publishedDate: String,
        val description: String?,
        val user: User,
        val userTotalPhotos: String,
        val device: String?,
        val location: String?,
        val tags: List<String>,
        val featured: String,
    )
}

internal class GetPhotoUseCaseImpl(
    private val detailsClient: DetailsClient,
) : GetPhotoUseCase {

    override suspend fun invoke(photoId: String): Result<GetPhotoUseCase.Photo> = detailsClient.getPhoto(photoId).fold(
        onSuccess = { response ->
            Result.success(
                GetPhotoUseCase.Photo(
                    id = response.id,
                    color = response.color?.fromHexColorToLong(),
                    views = response.views.toLong().withDecimalSeparator(),
                    downloads = response.downloads.toLong().withDecimalSeparator(),
                    likes = response.likes.toLong().withDecimalSeparator(),
                    publishedDate = Instant.parse(response.createdAt).toLocalDateTime(TimeZone.currentSystemDefault()).let { date ->
                        "Published on ${date.month.name.lowercase().replaceFirstChar { it.titlecase() }} ${date.dayOfMonth}, ${date.year}"
                    },
                    description = response.description,
                    user = response.user,
                    userTotalPhotos = "${response.user.totalPhotos.toLong().withDecimalSeparator()} photos",
                    device = response.exif?.name,
                    location = response.location.name,
                    tags = response.tags.map { it.title },
                    featured = response.topics.joinToString(),
                )
            )
        },
        onFailure = { Result.failure(it) },
    )
}
