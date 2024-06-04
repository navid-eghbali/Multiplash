package navid.multiplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class HomeViewModel(
    private val httpClient: HttpClient,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val request = httpClient.get {
                url("https://api.unsplash.com/photos?order_by=popular&orientation=squarish&per_page=40")
            }
            _state.update {
                try {
                    HomeUiState.Success(request.body())
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                    HomeUiState.Error(throwable.message.toString())
                }
            }
        }
    }

}
