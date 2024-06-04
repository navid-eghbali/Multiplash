package navid.multiplash.core.async.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import me.tatarka.inject.annotations.Provides
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.async.FireAndForgetCoroutineScope
import navid.multiplash.core.di.ApplicationScope

expect interface CoreAsyncPlatformComponent

interface CoreAsyncComponent : CoreAsyncPlatformComponent {

    @ApplicationScope
    @Provides
    fun provideFireAndForgetCoroutineScope(
        dispatchers: CoroutineDispatchers
    ): FireAndForgetCoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}
