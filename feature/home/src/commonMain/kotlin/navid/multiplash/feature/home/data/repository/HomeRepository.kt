package navid.multiplash.feature.home.data.repository

import navid.multiplash.feature.home.data.model.GetPhotos
import navid.multiplash.feature.home.data.remote.HomeClient

interface HomeRepository {

    suspend fun getPhotos(): Result<GetPhotos.Response>
}

internal class HomeRepositoryImpl(
    private val homeClient: HomeClient,
) : HomeRepository {

    override suspend fun getPhotos(): Result<GetPhotos.Response> = homeClient.getPhotos()
}
