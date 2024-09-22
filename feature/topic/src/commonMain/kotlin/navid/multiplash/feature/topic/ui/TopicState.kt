package navid.multiplash.feature.topic.ui

import androidx.compose.runtime.Immutable
import navid.multiplash.feature.topic.usecase.GetTopicUseCase

@Immutable
internal sealed interface TopicState {

    @Immutable
    data object Empty : TopicState

    @Immutable
    data object Loading : TopicState

    @Immutable
    data class Failure(val message: String?) : TopicState

    @Immutable
    data class Success(
        val topic: GetTopicUseCase.Topic,
    ) : TopicState
}
