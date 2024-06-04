package navid.multiplash.core.async.di

import kotlinx.coroutines.Dispatchers
import me.tatarka.inject.annotations.Provides
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.di.ApplicationScope

actual interface CoreAsyncPlatformComponent {

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )
}
