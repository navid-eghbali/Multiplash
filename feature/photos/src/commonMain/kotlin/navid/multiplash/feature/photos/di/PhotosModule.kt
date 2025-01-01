package navid.multiplash.feature.photos.di

import navid.multiplash.feature.photos.ui.PhotosScreen
import navid.multiplash.feature.photos.ui.PhotosViewModel
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.instance

val photosModule = DI.Module(name = "PhotosModule") {

    bindFactory { args: PhotosScreen ->
        PhotosViewModel(
            args = args,
            searchPhotosUseCase = instance(),
        )
    }

}
