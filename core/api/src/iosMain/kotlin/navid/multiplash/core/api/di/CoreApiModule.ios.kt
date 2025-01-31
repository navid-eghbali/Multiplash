package navid.multiplash.core.api.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import navid.multiplash.core.api.plugin.AuthorizationPlugin
import kotlin.time.Duration.Companion.seconds

actual fun provideHttpClient(): HttpClient = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.INFO
    }

    install(HttpTimeout) {
        connectTimeoutMillis = 30.seconds.inWholeMilliseconds
        requestTimeoutMillis = 30.seconds.inWholeMilliseconds
    }

    install(AuthorizationPlugin)
}
