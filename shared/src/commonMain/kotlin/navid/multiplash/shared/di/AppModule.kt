package navid.multiplash.shared.di

import navid.multiplash.core.api.di.coreApiModule
import navid.multiplash.core.async.di.coreAsyncModule
import navid.multiplash.ui.home.di.homeModule
import org.kodein.di.conf.ConfigurableDI

val appModule: ConfigurableDI = ConfigurableDI().addConfig {
    import(coreApiModule)
    import(coreAsyncModule)
    import(homeModule)
}
