package navid.multiplash.feature.topic.data.model

import navid.multiplash.core.data.Topic

internal object GetTopic {

    data class Response(val topic: Topic)

    fun getPath(topicId: String): String = "/topics/$topicId"
}
