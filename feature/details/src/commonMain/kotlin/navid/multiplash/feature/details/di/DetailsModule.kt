package navid.multiplash.feature.details.di

import navid.multiplash.feature.details.ui.DetailsViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val detailsModule = DI.Module(name = "DetailsModule") {

    bindProvider {
        DetailsViewModel(
            args = instance(),
        )
    }
}
