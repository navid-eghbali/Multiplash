package navid.multiplash.core.async.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.swing.Swing
import navid.multiplash.core.async.CoroutineDispatchers

actual fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchers(
    io = Dispatchers.IO,
    computation = Dispatchers.Default,
    main = Dispatchers.Swing,
)
