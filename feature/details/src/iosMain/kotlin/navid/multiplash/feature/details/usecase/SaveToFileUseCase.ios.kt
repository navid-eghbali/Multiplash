package navid.multiplash.feature.details.usecase

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToFile
import platform.Photos.PHAssetChangeRequest
import platform.Photos.PHPhotoLibrary
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalForeignApi::class)
actual class SaveToFileUseCase {
    actual suspend operator fun invoke(fileName: String, data: ByteArray): Result<String> = suspendCoroutine { continuation ->
        PHPhotoLibrary.sharedPhotoLibrary().performChanges(
            {
                data.usePinned { pinned ->
                    val imageData = NSData.dataWithBytes(pinned.addressOf(0), data.size.toULong())
                    val tempFileUrl = NSTemporaryDirectory() + "/$fileName"
                    imageData.writeToFile(tempFileUrl, true)
                    val fileUrl = NSURL.fileURLWithPath(tempFileUrl)
                    PHAssetChangeRequest.creationRequestForAssetFromImageAtFileURL(fileUrl)
                }
            }, completionHandler = { success, error ->
                if (success) {
                    continuation.resume(Result.success("Photos app"))
                } else {
                    continuation.resumeWithException(IllegalStateException(error?.localizedDescription))
                }
            }
        )
    }
}
