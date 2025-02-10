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
        val publishedDateYear: String,
        val publishedDateMonth: String,
        val publishedDateDay: String,
        val description: String?,
        val user: User,
        val userTotalPhotos: String,
        val device: String?,
        val location: String?,
        val tags: List<String>,
        val featured: String,
        val downloadLink: String,
    )
}

internal class GetPhotoUseCaseImpl(
    private val detailsClient: DetailsClient,
) : GetPhotoUseCase {

    override suspend fun invoke(photoId: String): Result<GetPhotoUseCase.Photo> = detailsClient.getPhoto(photoId).fold(
        onSuccess = { response ->
            with(response) {
                val date = Instant.parse(createdAt).toLocalDateTime(TimeZone.currentSystemDefault())
                Result.success(
                    GetPhotoUseCase.Photo(
                        id = id,
                        color = color?.fromHexColorToLong(),
                        views = views.toLong().withDecimalSeparator(),
                        downloads = downloads.toLong().withDecimalSeparator(),
                        likes = likes.toLong().withDecimalSeparator(),
                        publishedDateYear = date.year.toString(),
                        publishedDateMonth = date.month.name.lowercase().replaceFirstChar { it.titlecase() },
                        publishedDateDay = date.dayOfMonth.toString(),
                        description = description,
                        user = user,
                        userTotalPhotos = user.totalPhotos.toLong().withDecimalSeparator(),
                        device = exif?.name,
                        location = location.name,
                        tags = tags.map { it.title },
                        featured = topics.joinToString(),
                        downloadLink = links.download,
                    )
                )
            }
        },
        onFailure = { Result.failure(it) },
    )
}
