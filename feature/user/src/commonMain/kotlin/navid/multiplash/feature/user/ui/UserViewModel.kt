package navid.multiplash.feature.user.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserViewModel(
    args: UserScreen,
) : ViewModel() {

    private val _state = MutableStateFlow(UserState)
    val state = _state.asStateFlow()
}
