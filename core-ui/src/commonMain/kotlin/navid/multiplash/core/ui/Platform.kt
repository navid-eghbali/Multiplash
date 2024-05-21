package navid.multiplash.core.ui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
