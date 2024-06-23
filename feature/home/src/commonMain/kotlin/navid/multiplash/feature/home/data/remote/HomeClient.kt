package navid.multiplash.feature.home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.LinkHeader
import navid.multiplash.feature.home.data.model.GetPhotos
import navid.multiplash.feature.home.data.model.Photo

interface HomeClient {

    suspend fun getPhotos(offset: Int): Result<GetPhotos.Response>
}

internal class HomeClientImpl(
    private val httpClient: HttpClient,
) : HomeClient {

    override suspend fun getPhotos(offset: Int): Result<GetPhotos.Response> = try {
        val request = httpClient.get {
            url("https://api.unsplash.com/photos?order_by=popular&orientation=squarish&per_page=30&page=$offset")
        }
        val l: LinkHeader
        request.headers[HttpHeaders.Link]
        val response = request.body<List<Photo>>()
        Result.success(GetPhotos.Response(response))
    } catch (e: Exception) {
        Result.failure(e)
    }
}
