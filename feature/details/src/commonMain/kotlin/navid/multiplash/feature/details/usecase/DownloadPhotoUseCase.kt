package navid.multiplash.feature.details.usecase

import navid.multiplash.feature.details.data.remote.DetailsClient
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal fun interface DownloadPhotoUseCase {
    suspend operator fun invoke(
        photoId: String,
        url: String,
    ): Result<String>
}

@OptIn(ExperimentalTime::class)
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
