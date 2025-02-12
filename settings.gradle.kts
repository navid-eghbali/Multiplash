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
    ":common",
    ":core:api",
    ":core:async",
    ":core:data",
    ":core:db",
    ":core:di",
    ":core:resources",
    ":core:ui",
    ":desktop-app",
    ":feature:details",
    ":feature:explore",
    ":feature:library",
    ":feature:photos",
    ":feature:search:api",
    ":feature:search:implementation",
    ":feature:topic",
    ":feature:user",
    ":shared",
)
