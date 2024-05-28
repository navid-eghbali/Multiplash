package navid.multiplash.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject
import navid.multiplash.core.api.data.Photo
import navid.multiplash.core.async.CoroutineDispatchers

@Inject
class HomeViewModel(
    private val dispatchers: CoroutineDispatchers,
    private val httpClient: HttpClient,
) : ViewModel() {

    val text: String = "Hello"

    init {
        viewModelScope.launch(dispatchers.io) {
            val request = httpClient.get {
                url("https://api.unsplash.com/photos?order_by=popular&orientation=squarish&per_page=40")
            }
            val response: List<Photo> = try {
                request.body()
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                emptyList()
            }
            println(response)
        }
    }

}
