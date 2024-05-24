package navid.multiplash.core.api.di

import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Provides
import navid.multiplash.core.api.getPlatformHttpClient
import navid.multiplash.core.di.ApplicationScope

interface CoreApiComponent {

    @ApplicationScope
    @Provides
    fun provideHttpClient(): HttpClient = getPlatformHttpClient()
}
