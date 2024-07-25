package navid.multiplash.shared.di

import navid.multiplash.core.api.di.coreApiModule
import navid.multiplash.core.async.di.coreAsyncModule
import navid.multiplash.feature.details.di.detailsModule
import navid.multiplash.feature.home.di.homeModule
import navid.multiplash.shared.ui.AppViewModel
import org.kodein.di.bindProvider
import org.kodein.di.conf.ConfigurableDI

val appModule: ConfigurableDI = ConfigurableDI().addConfig {
    import(coreApiModule)
    import(coreAsyncModule)
    import(detailsModule)
    import(homeModule)

    bindProvider {
        AppViewModel()
    }
}
