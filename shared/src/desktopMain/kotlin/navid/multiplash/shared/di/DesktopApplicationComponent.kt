package navid.multiplash.shared.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Component
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.di.ApplicationScope

@Component
@ApplicationScope
abstract class DesktopApplicationComponent : SharedApplicationComponent {

    abstract val dispatchers: CoroutineDispatchers

    companion object
}

internal actual fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
