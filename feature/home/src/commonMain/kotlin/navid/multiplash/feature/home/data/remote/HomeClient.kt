package navid.multiplash.feature.home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import navid.multiplash.feature.home.data.model.GetPhotos
import navid.multiplash.feature.home.data.model.Photo

internal interface HomeClient {

    suspend fun getPhotos(): Result<GetPhotos.Response>
}

internal class HomeClientImpl(
    private val httpClient: HttpClient,
) : HomeClient {

    override suspend fun getPhotos(): Result<GetPhotos.Response> = try {
        val request = httpClient.get {
            url("https://api.unsplash.com/photos?order_by=popular&orientation=squarish&per_page=40")
        }
        val response = request.body<List<Photo>>()
        Result.success(GetPhotos.Response(response))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
