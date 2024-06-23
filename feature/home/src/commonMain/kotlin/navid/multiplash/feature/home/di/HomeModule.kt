package navid.multiplash.feature.home.di

import navid.multiplash.feature.home.data.remote.HomeClient
import navid.multiplash.feature.home.data.remote.HomeClientImpl
import navid.multiplash.feature.home.data.repository.HomeRepository
import navid.multiplash.feature.home.data.repository.HomeRepositoryImpl
import navid.multiplash.feature.home.ui.HomeViewModel
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

    bindProvider {
        HomeViewModel(
            homeRepository = instance(),
        )
    }
}
