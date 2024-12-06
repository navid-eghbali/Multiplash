package navid.multiplash.feature.details.usecase

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import navid.multiplash.common.ext.fromHexColorToLong
import navid.multiplash.common.ext.withDecimalSeparator
import navid.multiplash.core.data.Exif
import navid.multiplash.core.data.Tag
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
        val user: User,
        val exif: Exif?,
        val tags: List<Tag>,
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
                    views = response.views.withDecimalSeparator(),
                    downloads = response.downloads.withDecimalSeparator(),
                    likes = response.likes.withDecimalSeparator(),
                    publishedDate = Instant.parse(response.createdAt).toLocalDateTime(TimeZone.currentSystemDefault()).let {
                        "Published on ${it.month.name} ${it.dayOfMonth}, ${it.year}"
                    },
                    user = response.user,
                    exif = response.exif,
                    tags = response.tags,
                    featured = response.topics.joinToString(),
                )
            )
        },
        onFailure = { Result.failure(it) },
    )
}
