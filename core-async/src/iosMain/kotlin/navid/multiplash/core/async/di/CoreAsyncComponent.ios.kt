package navid.multiplash.core.async.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
internal actual fun ioDispatcher(): CoroutineDispatcher = newSingleThreadContext("IODispatcher")
