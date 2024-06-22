package navid.multiplash.core.api.di

import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val coreApiModule = DI.Module(name = "CoreApiModule") {

    bindSingleton<HttpClient> { provideHttpClient() }
}

expect fun provideHttpClient(): HttpClient
