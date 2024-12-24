package navid.multiplash.shared.di

import navid.multiplash.core.api.di.coreApiModule
import navid.multiplash.core.async.di.coreAsyncModule
import navid.multiplash.feature.details.di.detailsModule
import navid.multiplash.feature.explore.di.exploreModule
import navid.multiplash.feature.library.di.libraryModule
import navid.multiplash.feature.photos.di.photosModule
import navid.multiplash.feature.search.di.searchModule
import navid.multiplash.feature.topic.di.topicModule
import navid.multiplash.shared.ui.AppViewModel
import org.kodein.di.bindProvider
import org.kodein.di.conf.ConfigurableDI

val appModule: ConfigurableDI = ConfigurableDI().addConfig {
    import(coreApiModule)
    import(coreAsyncModule)
    import(detailsModule)
    import(exploreModule)
    import(libraryModule)
    import(photosModule)
    import(searchModule)
    import(topicModule)

    bindProvider {
        AppViewModel()
    }
}
