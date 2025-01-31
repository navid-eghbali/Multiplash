package navid.multiplash.feature.details.usecase

expect class SaveToFileUseCase {
    suspend operator fun invoke(
        fileName: String,
        data: ByteArray,
    ): Result<String>
}
