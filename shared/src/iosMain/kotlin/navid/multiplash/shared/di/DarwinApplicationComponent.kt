package navid.multiplash.shared.di

import me.tatarka.inject.annotations.Component
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.di.ApplicationScope

@Component
@ApplicationScope
abstract class DarwinApplicationComponent : SharedApplicationComponent {

    abstract val dispatchers: CoroutineDispatchers

    companion object
}
