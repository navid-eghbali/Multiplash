package navid.multiplash.feature.photos.di

import navid.multiplash.feature.photos.data.remote.PhotosClient
import navid.multiplash.feature.photos.data.remote.PhotosClientImpl
import navid.multiplash.feature.photos.data.repository.PhotosRepository
import navid.multiplash.feature.photos.data.repository.PhotosRepositoryImpl
import navid.multiplash.feature.photos.ui.PhotosScreen
import navid.multiplash.feature.photos.ui.PhotosViewModel
import navid.multiplash.feature.photos.usecase.GetPhotosUseCase
import navid.multiplash.feature.photos.usecase.GetPhotosUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val photosModule = DI.Module(name = "PhotosModule") {

    bindSingleton<PhotosClient> {
        PhotosClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<PhotosRepository> {
        PhotosRepositoryImpl(
            photosClient = instance(),
        )
    }

    bindSingleton<GetPhotosUseCase> {
        GetPhotosUseCaseImpl(
            photosRepository = instance(),
        )
    }

    bindFactory { args: PhotosScreen ->
        PhotosViewModel(
            args = args,
            getPhotosUseCase = instance(),
        )
    }

}
