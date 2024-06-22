package navid.multiplash.ui.home.di

import navid.multiplash.ui.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val homeModule = DI.Module(name = "HomeModule") {
    bindProvider {
        HomeViewModel(
            httpClient = instance(),
        )
    }
}
