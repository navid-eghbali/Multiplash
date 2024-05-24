package navid.multiplash.shared.di

import android.app.Application
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.di.ApplicationScope

@Component
@ApplicationScope
abstract class AndroidApplicationComponent(
    @get:Provides val application: Application,
) : SharedApplicationComponent {

    abstract val dispatchers: CoroutineDispatchers

    companion object
}

internal actual fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
