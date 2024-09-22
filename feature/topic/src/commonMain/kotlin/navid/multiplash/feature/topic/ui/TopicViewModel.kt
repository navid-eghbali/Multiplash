package navid.multiplash.feature.topic.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.topic.usecase.GetTopicUseCase

internal class TopicViewModel(
    private val args: TopicScreen,
    private val getTopicUseCase: GetTopicUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<TopicState> = MutableStateFlow(TopicState.Empty)
    val state = _state
        .onStart { fetchTopic(topicId = args.id) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = TopicState.Empty,
        )

    fun onReload() {
        fetchTopic(topicId = args.id)
    }

    private fun fetchTopic(topicId: String) {
        viewModelScope.launch {
            _state.update { TopicState.Loading }
            getTopicUseCase(topicId).fold(
                onSuccess = { topic ->
                    _state.update { TopicState.Success(topic) }
                },
                onFailure = { throwable ->
                    println(throwable.printStackTrace())
                    _state.update { TopicState.Failure(throwable.message) }
                },
            )
        }
    }
}
