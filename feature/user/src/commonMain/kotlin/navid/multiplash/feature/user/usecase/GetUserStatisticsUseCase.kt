package navid.multiplash.feature.user.usecase

import navid.multiplash.common.ext.withDecimalSeparator
import navid.multiplash.feature.user.data.remote.UserClient

internal fun interface GetUserStatisticsUseCase {

    suspend operator fun invoke(username: String): Result<Stats>

    data class Stats(
        val totalDownloads: String,
        val totalViews: String,
    )

}

internal class GetUserStatisticsUseCaseImpl(
    private val userClient: UserClient,
) : GetUserStatisticsUseCase {

    override suspend fun invoke(username: String): Result<GetUserStatisticsUseCase.Stats> = userClient.getUserStatistics(username).fold(
        onSuccess = { response ->
            with(response) {
                Result.success(
                    GetUserStatisticsUseCase.Stats(
                        totalDownloads = downloads.total.withDecimalSeparator(),
                        totalViews = views.total.withDecimalSeparator(),
                    )
                )
            }
        },
        onFailure = { Result.failure(it) }
    )

}
