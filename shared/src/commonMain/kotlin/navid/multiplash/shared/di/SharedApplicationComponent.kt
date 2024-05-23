package navid.multiplash.shared.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.async.FireAndForgetCoroutineScope
import navid.multiplash.core.di.ApplicationScope

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent : SharedPlatformApplicationComponent {

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @ApplicationScope
    @Provides
    fun provideFireAndForgetCoroutineScope(
        dispatchers: CoroutineDispatchers
    ): FireAndForgetCoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}
