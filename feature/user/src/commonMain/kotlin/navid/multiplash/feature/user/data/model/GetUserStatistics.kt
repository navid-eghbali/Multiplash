package navid.multiplash.feature.user.data.model

import kotlinx.serialization.Serializable

internal object GetUserStatistics {

    @Serializable
    data class Response(
        val id: String,
        val username: String,
        val downloads: Statistics,
        val views: Statistics,
    ) {
        @Serializable
        data class Statistics(
            val total: Long,
            val historical: History,
        )

        @Serializable
        data class History(
            val change: Long,
            val average: Long,
            val resolution: String,
            val quantity: Long,
        )
    }

    fun getPath(username: String): String = "/users/$username/statistics"
}
