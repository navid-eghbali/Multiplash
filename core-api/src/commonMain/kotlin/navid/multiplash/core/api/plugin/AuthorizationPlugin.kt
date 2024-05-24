package navid.multiplash.core.api.plugin

import io.ktor.client.plugins.api.createClientPlugin
import navid.multiplash.core.api.BuildKonfig

internal val AuthorizationPlugin = createClientPlugin(
    name = "AuthorizationPlugin",
    createConfiguration = ::AuthorizationPluginConfig
) {
    val headerName = pluginConfig.headerName
    val headerValue = pluginConfig.headerValue
    onRequest { request, _ ->
        request.headers.append(headerName, headerValue)
    }
}

internal class AuthorizationPluginConfig {
    val headerName: String = "Authorization"
    val headerValue: String = "Client-ID ${BuildKonfig.UNSPLASH_CLIENT_ID}"
}
