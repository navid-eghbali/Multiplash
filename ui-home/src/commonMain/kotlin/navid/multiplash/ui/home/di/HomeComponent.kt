package navid.multiplash.ui.home.di

import me.tatarka.inject.annotations.Component
import navid.multiplash.core.api.di.CoreApiComponent
import navid.multiplash.core.di.ApplicationScope
import navid.multiplash.ui.home.HomeViewModel

@Component
@ApplicationScope
abstract class HomeComponent : CoreApiComponent {

    abstract val homeViewModel: HomeViewModel

    companion object
}

expect fun createHomeComponent(): HomeComponent
