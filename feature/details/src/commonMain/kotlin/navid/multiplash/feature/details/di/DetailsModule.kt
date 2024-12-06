package navid.multiplash.feature.details.di

import navid.multiplash.feature.details.data.remote.DetailsClient
import navid.multiplash.feature.details.data.remote.DetailsClientImpl
import navid.multiplash.feature.details.ui.DetailsScreen
import navid.multiplash.feature.details.ui.DetailsViewModel
import navid.multiplash.feature.details.usecase.GetPhotoUseCase
import navid.multiplash.feature.details.usecase.GetPhotoUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val detailsModule = DI.Module(name = "DetailsModule") {

    bindSingleton<DetailsClient> {
        DetailsClientImpl(
            httpClient = instance(),
        )
    }

    bindProvider<GetPhotoUseCase> {
        GetPhotoUseCaseImpl(
            detailsClient = instance(),
        )
    }

    bindFactory { args: DetailsScreen ->
        DetailsViewModel(
            args = args,
            getPhotoUseCase = instance(),
        )
    }
}
