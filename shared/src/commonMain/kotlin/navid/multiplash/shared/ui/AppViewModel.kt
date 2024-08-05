package navid.multiplash.shared.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()
}
