package navid.multiplash.feature.home.di

import navid.multiplash.feature.home.data.remote.HomeClient
import navid.multiplash.feature.home.data.remote.HomeClientImpl
import navid.multiplash.feature.home.data.repository.HomeRepository
import navid.multiplash.feature.home.data.repository.HomeRepositoryImpl
import navid.multiplash.feature.home.ui.HomeViewModel
import navid.multiplash.feature.home.usecase.GetPhotosUseCase
import navid.multiplash.feature.home.usecase.GetPhotosUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val homeModule = DI.Module(name = "HomeModule") {

    bindSingleton<HomeClient> {
        HomeClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<HomeRepository> {
        HomeRepositoryImpl(
            homeClient = instance(),
        )
    }

    bindSingleton<GetPhotosUseCase> {
        GetPhotosUseCaseImpl(
            homeRepository = instance(),
        )
    }

    bindProvider {
        HomeViewModel(
            getPhotosUseCase = instance(),
        )
    }
}
