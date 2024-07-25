enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Multiplash"
include(
    ":android-app",
    ":core:api",
    ":core:async",
    ":core:di",
    ":core:resources",
    ":core:ui",
    ":desktop-app",
    ":feature:details",
    ":feature:home",
    ":kodein-viewmodel",
    ":shared",
)
