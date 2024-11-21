package navid.multiplash.feature.topic.ui

import androidx.compose.runtime.Immutable
import navid.multiplash.feature.topic.usecase.GetTopicUseCase

@Immutable
internal data class TopicState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val topic: GetTopicUseCase.Topic? = null,
)
