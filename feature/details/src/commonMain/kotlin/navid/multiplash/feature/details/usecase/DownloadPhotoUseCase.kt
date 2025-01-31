package navid.multiplash.feature.details.usecase

import kotlinx.datetime.Clock
import navid.multiplash.feature.details.data.remote.DetailsClient

internal fun interface DownloadPhotoUseCase {
    suspend operator fun invoke(
        photoId: String,
        url: String,
    ): Result<String>
}

internal class DownloadPhotoUseCaseImpl(
    private val detailsClient: DetailsClient,
    private val saveToFileUseCase: SaveToFileUseCase,
) : DownloadPhotoUseCase {

    override suspend fun invoke(photoId: String, url: String): Result<String> =
        detailsClient.downloadPhoto(url)
            .fold(
                onSuccess = { photo ->
                    val fileName = "downloaded_image_${Clock.System.now().toEpochMilliseconds()}.jpg"
                    saveToFileUseCase(fileName, photo)
                },
                onFailure = { Result.failure(it) },
            )
}
