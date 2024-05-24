package navid.multiplash.core.api

import io.ktor.client.HttpClient

internal expect fun getPlatformHttpClient(): HttpClient
