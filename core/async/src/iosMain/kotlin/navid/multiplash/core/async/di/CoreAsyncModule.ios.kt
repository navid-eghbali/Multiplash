package navid.multiplash.core.async.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import navid.multiplash.core.async.CoroutineDispatchers

actual fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
    io = newSingleThreadContext("IODispatcher"),
    computation = Dispatchers.Default,
    main = Dispatchers.Main,
)
