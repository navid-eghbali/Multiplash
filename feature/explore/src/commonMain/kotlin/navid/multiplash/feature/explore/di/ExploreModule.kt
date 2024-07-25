package navid.multiplash.feature.explore.di

import navid.multiplash.feature.explore.data.remote.ExploreClient
import navid.multiplash.feature.explore.data.remote.ExploreClientImpl
import navid.multiplash.feature.explore.data.repository.ExploreRepository
import navid.multiplash.feature.explore.data.repository.ExploreRepositoryImpl
import navid.multiplash.feature.explore.ui.ExploreViewModel
import navid.multiplash.feature.explore.usecase.GetPhotosUseCase
import navid.multiplash.feature.explore.usecase.GetPhotosUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val exploreModule = DI.Module(name = "ExploreModule") {

    bindSingleton<ExploreClient> {
        ExploreClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<ExploreRepository> {
        ExploreRepositoryImpl(
            exploreClient = instance(),
        )
    }

    bindSingleton<GetPhotosUseCase> {
        GetPhotosUseCaseImpl(
            exploreRepository = instance(),
        )
    }

    bindProvider {
        ExploreViewModel(
            getPhotosUseCase = instance(),
        )
    }
}
