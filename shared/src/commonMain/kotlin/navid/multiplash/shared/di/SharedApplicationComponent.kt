package navid.multiplash.shared.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import navid.multiplash.core.api.di.CoreApiComponent
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.async.FireAndForgetCoroutineScope
import navid.multiplash.core.di.ApplicationScope

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent :
    SharedPlatformApplicationComponent,
    CoreApiComponent {

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
        io = ioDispatcher(),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )

    @ApplicationScope
    @Provides
    fun provideFireAndForgetCoroutineScope(
        dispatchers: CoroutineDispatchers
    ): FireAndForgetCoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}

internal expect fun ioDispatcher(): CoroutineDispatcher
