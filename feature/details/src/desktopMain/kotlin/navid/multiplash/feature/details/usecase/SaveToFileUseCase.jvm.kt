package navid.multiplash.feature.details.usecase

import java.io.File

actual class SaveToFileUseCase {
    actual suspend operator fun invoke(fileName: String, data: ByteArray): Result<String> = runCatching {
        val file = File(System.getProperty("user.home") + "/Desktop", fileName)
        file.writeBytes(data)
        file.absolutePath
    }
}
