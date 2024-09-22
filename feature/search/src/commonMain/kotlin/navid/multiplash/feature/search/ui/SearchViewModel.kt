package navid.multiplash.feature.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import navid.multiplash.feature.search.usecase.GetTopicsUseCase
import navid.multiplash.feature.search.usecase.SearchPhotosUseCase

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class SearchViewModel(
    private val getTopicsUseCase: GetTopicsUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart { fetchTopics() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SearchState()
        )
    val pagedPhotos = _state
        .filterNot { it.query.isEmpty() }
        .map { it.query }
        .debounce(500)
        .flatMapLatest { searchPhotosUseCase(it) }
        .cachedIn(viewModelScope)

    fun onQueryChange(text: String) {
        _state.update { it.copy(query = text) }
    }

    private fun fetchTopics() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getTopicsUseCase().fold(
                onSuccess = { topics ->
                    _state.update { it.copy(isLoading = false, topics = topics) }
                },
                onFailure = { println(it.printStackTrace()) },
            )
        }
    }
}
