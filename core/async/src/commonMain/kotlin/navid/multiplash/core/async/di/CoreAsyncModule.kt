package navid.multiplash.core.async.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.async.FireAndForgetCoroutineScope
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val coreAsyncModule = DI.Module(name = "CoreAsyncModule") {

    bindSingleton<CoroutineDispatchers> { provideCoroutineDispatchers() }

    bindSingleton<FireAndForgetCoroutineScope> {
        val dispatchers = instance<CoroutineDispatchers>()
        CoroutineScope(dispatchers.main + SupervisorJob())
    }
}

expect fun provideCoroutineDispatchers(): CoroutineDispatchers
