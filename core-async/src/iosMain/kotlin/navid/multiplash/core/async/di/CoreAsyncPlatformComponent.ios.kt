package navid.multiplash.core.async.di

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import me.tatarka.inject.annotations.Provides
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.di.ApplicationScope

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
actual interface CoreAsyncPlatformComponent {

    @ApplicationScope
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
        io = newSingleThreadContext("IODispatcher"),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )
}
