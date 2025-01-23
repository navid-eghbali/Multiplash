package navid.multiplash.feature.search.usecase

import navid.multiplash.common.ext.fromHexColorToLong
import navid.multiplash.common.ext.withDecimalSeparator
import navid.multiplash.feature.search.data.remote.SearchClient

internal fun interface GetTopicsUseCase {

    suspend operator fun invoke(): Result<List<Topic>>

    data class Topic(
        val id: String,
        val title: String,
        val totalPhotos: String,
        val color: Long?,
        val coverUrl: String,
    )
}

internal class GetTopicsUseCaseImpl(
    private val searchClient: SearchClient
) : GetTopicsUseCase {

    override suspend fun invoke(): Result<List<GetTopicsUseCase.Topic>> = searchClient.getTopics().fold(
        onSuccess = { response ->
            Result.success(
                response.topics.map {
                    GetTopicsUseCase.Topic(
                        id = it.id,
                        title = it.title,
                        totalPhotos = "${it.totalPhotos.toLong().withDecimalSeparator()} photos",
                        color = it.coverPhoto.color?.fromHexColorToLong(),
                        coverUrl = it.coverPhoto.urls.small,
                    )
                }
            )
        },
        onFailure = { Result.failure(it) },
    )
}
