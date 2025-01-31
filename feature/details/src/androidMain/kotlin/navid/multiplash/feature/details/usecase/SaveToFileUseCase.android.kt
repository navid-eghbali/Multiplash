package navid.multiplash.feature.details.usecase

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore

actual class SaveToFileUseCase(
    private val context: Context,
) {
    actual suspend operator fun invoke(fileName: String, data: ByteArray): Result<String> {
        val resolver = context.contentResolver
        return resolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        )?.let { imageUri ->
            resolver.openOutputStream(imageUri)?.use { outputStream ->
                outputStream.write(data)
                Result.success(imageUri.toString())
            }
        } ?: Result.failure(IllegalStateException("Failed to create MediaStore entry."))
    }
}
