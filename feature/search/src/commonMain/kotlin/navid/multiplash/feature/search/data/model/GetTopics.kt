package navid.multiplash.feature.search.data.model

import io.ktor.http.Parameters
import io.ktor.http.parameters
import navid.multiplash.core.data.Topic

internal object GetTopics {

    data class Response(val topics: List<Topic>)

    fun getPath(): String = "/topics"

    fun getQueryParameters(): Parameters = parameters {
        append("order_by", "featured")
        append("per_page", "30")
        append("page", "1")
    }
}
