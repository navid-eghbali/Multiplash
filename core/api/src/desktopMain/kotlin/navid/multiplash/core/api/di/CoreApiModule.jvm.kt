package navid.multiplash.core.api.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import navid.multiplash.core.api.plugin.AuthorizationPlugin
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

actual fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
    engine {
        config {
            connectTimeout(30.seconds.toJavaDuration())
            readTimeout(30.seconds.toJavaDuration())
            retryOnConnectionFailure(true)
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.INFO
    }

    install(AuthorizationPlugin)
}
