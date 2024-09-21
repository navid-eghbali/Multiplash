package navid.multiplash.feature.details.di

import navid.multiplash.feature.details.ui.DetailsScreen
import navid.multiplash.feature.details.ui.DetailsViewModel
import org.kodein.di.DI
import org.kodein.di.bindFactory

val detailsModule = DI.Module(name = "DetailsModule") {

    bindFactory { args: DetailsScreen ->
        DetailsViewModel(
            args = args,
        )
    }
}
